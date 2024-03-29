import 'dart:math';
import 'dart:ui';

import 'package:app_flutter/widgets/profile_background.dart';
import 'package:flutter/material.dart';
import 'package:flutter_lorem/flutter_lorem.dart';

import '../model/user_model.dart';

class DetailScreen extends StatefulWidget {
  final User user;

  const DetailScreen({Key? key, required this.user}) : super(key: key);

  @override
  State<DetailScreen> createState() => _DetailScreenState();
}

class _DetailScreenState extends State<DetailScreen>
    with SingleTickerProviderStateMixin {
  late ScrollController scrollController;
  bool fabIsVisible = false;
  String longText = lorem(paragraphs: 4, words: 500);

  @override
  void initState() {
    scrollController = ScrollController();
    scrollController.addListener(() {
      if (scrollController.offset <=
              scrollController.position.minScrollExtent &&
          !scrollController.position.outOfRange) {
        setState(() {
          fabIsVisible = false;
        });
      } else {
        setState(() {
          fabIsVisible = true;
        });
      }
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    Widget profile = CustomScrollView(
      key: const Key("scrollview"),
      controller: scrollController,
      slivers: [
        SliverPadding(
            sliver: SliverPersistentHeader(
              pinned: true,
              delegate: _SliverAppBarDelegate(
                  minHeight: 90.0, maxHeight: 200.0, user: widget.user),
            ),
            padding: const EdgeInsets.only(bottom: 70)),
        SliverList(
            delegate: SliverChildListDelegate([
          Container(
            padding: const EdgeInsets.only(left: 15, right: 15, bottom: 100),
            child: Text(longText),
          )
        ]))
      ],
    );

    return Scaffold(
        body: Container(
      alignment: Alignment.center,
      child: Stack(children: [
        profile,
        //Fab
        Positioned(
            right: 30,
            bottom: 30,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 500),
              opacity: fabIsVisible ? 1.0 : 0.0,
              child: Container(
                width: 50,
                height: 50,
                decoration: const BoxDecoration(
                    color: Colors.blue, shape: BoxShape.circle),
                child: IconButton(
                  key: const Key("iconButton"),
                  icon: const Icon(Icons.add_a_photo),
                  iconSize: 25,
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                ),
              ),
            )),
      ]),
    ));
  }
}

class _SliverAppBarDelegate extends SliverPersistentHeaderDelegate {
  _SliverAppBarDelegate(
      {required this.minHeight, required this.maxHeight, required this.user});

  final double minHeight;
  final double maxHeight;
  final User user;
  double imgRadius = 50.0;

  @override
  double get maxExtent => maxHeight;

  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    var progress = shrinkOffset / maxHeight;
    var size = MediaQuery.of(context).size;
    var width = size.width;
    return Stack(
      fit: StackFit.expand,
      clipBehavior: Clip.none,
      children: <Widget>[
        Container(
          height: lerpDouble(maxHeight + imgRadius + 10, minHeight, progress),
        ),
        //Background
        CustomPaint(
          painter: ProfileBackground(),
          size: Size(double.infinity, maxHeight),
        ),
        //User Name
        Positioned(
            right: lerpDouble(width * .5 - imgRadius, 30, progress),
            top:
                lerpDouble(maxHeight - imgRadius * 2, minHeight * .5, progress),
            child: Text(
              user.name,
              overflow: TextOverflow.clip,
              style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
              textScaleFactor: lerpDouble(1, 0.65, progress),
            )),
        //Image
        Positioned(
          left: lerpDouble(width / 2 - imgRadius, 20, progress),
          top: lerpDouble(maxHeight - imgRadius,
              minHeight / 2 - imgRadius * 0.20, progress),
          child: Stack(
            alignment: Alignment.center,
            children: [
              TweenAnimationBuilder(
                tween: Tween<double>(begin: 0.0, end: 4.0 * pi),
                duration: const Duration(seconds: 2),
                builder: (_, double angle, __) {
                  return Transform.rotate(
                      angle: angle,
                      child: SizedBox(
                        width: lerpDouble(imgRadius * 2, 20, progress),
                        height: lerpDouble(imgRadius * 2, 20, progress),
                        child: CustomPaint(
                          painter: CircleBackground(
                              radius: lerpDouble(
                                  imgRadius + 10, 20 + 8, progress)!),
                        ),
                      ));
                },
              ),
              CircleAvatar(
                backgroundImage: NetworkImage(user.photoUrl),
                radius: lerpDouble(imgRadius, 20, progress),
              ),
            ],
          ),
        ),
      ],
    );
  }

  @override
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }
}

class CircleBackground extends CustomPainter {
  CircleBackground({required this.radius});

  final double radius;

  @override
  void paint(Canvas canvas, Size size) {
    var linearGradient = LinearGradient(
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
        colors: [Colors.blue.shade800, Colors.red.shade800]);

    var centerX = size.width / 2;
    var centerY = size.height / 2;
    var offset = Offset(centerX, centerY);

    var paint = Paint()
      ..isAntiAlias = true
      ..shader = linearGradient
          .createShader(Rect.fromCircle(center: offset, radius: radius));

    canvas.drawCircle(offset, radius, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }
}
