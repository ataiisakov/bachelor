import 'dart:ui';
import 'dart:math';

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

class _DetailScreenState extends State<DetailScreen> with SingleTickerProviderStateMixin{
  late AnimationController controller;
  late Animation<double> animation;

  @override
  void initState() {
    super.initState();
    controller = AnimationController(
        vsync: this,
        duration: const Duration(seconds: 2)
    );
    animation = Tween<double>(begin: 0.0, end: 2.0).animate(controller);
    controller.forward(from: 0.0);
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    Widget profile = CustomScrollView(
      slivers: [
        SliverPadding(
            sliver: SliverPersistentHeader(
              pinned: true,
              delegate: _SliverAppBarDelegate(
                  minHeight: 90.0, maxHeight: 200.0, user: widget.user, animation: animation),
            ),
            padding: const EdgeInsets.only(bottom: 70)),
        SliverList(
            delegate: SliverChildListDelegate([
          Container(
            padding: const EdgeInsets.only(left: 15, right: 15, bottom: 100),
            child: Text(lorem(paragraphs: 4, words: 500)),
          ),
        ]))
      ],
    );

    return Scaffold(
        body: Container(
            width: window.physicalSize.width,
            alignment: Alignment.center,
            child: profile));
  }
}

class _SliverAppBarDelegate extends SliverPersistentHeaderDelegate {
  _SliverAppBarDelegate(
      {required this.minHeight, required this.maxHeight, required this.user,
      required this.animation});

  final double minHeight;
  final double maxHeight;
  final Animation<double> animation;
  final User user;
  double imgRadius = 50.0;

  @override
  Widget build(
      BuildContext context, double shrinkOffset, bool overlapsContent) {
    var progress = shrinkOffset / maxHeight;
    var size = MediaQuery.of(context).size;
    var width = size.width;
    var height = size.height;
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
            left:
                lerpDouble((width / 2.0) - (imgRadius), width - 100, progress),
            top: lerpDouble(maxHeight - imgRadius * 2, minHeight / 2, progress),
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
                minHeight / 2 - imgRadius * 0.40, progress),
            child: Stack(
              children: [
                /*Container(
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      gradient: LinearGradient(
                          begin: Alignment.topLeft,
                          end: Alignment.bottomRight,
                          colors: [Colors.blue.shade800, Colors.red.shade800]
                      ),
                    ),
                )*/
                Container(
                  child: CustomPaint(
                    painter: CircleBackground(radius: imgRadius),
                  ),
                ),
                Container(
                  child: CircleAvatar(
                    backgroundImage: NetworkImage(user.photoUrl),
                    radius: lerpDouble(imgRadius, 20, progress),
                  ),
                ),
              ],
            ),
            // child: RotationTransition(
            //   turns: animation,
            //   child: Stack(
            //     children: [
            //
            //     ],
            //   ),
            //     child: Container(
            //       decoration: BoxDecoration(
            //         shape: BoxShape.circle,
            //         gradient: LinearGradient(
            //             begin: Alignment.topLeft,
            //             end: Alignment.bottomRight,
            //             colors: [Colors.blue.shade800, Colors.red.shade800]
            //         ),
            //         // image: DecorationImage(image: NetworkImage(user.photoUrl))
            //       ),
            //       child: Padding(
            //         padding: const EdgeInsets.all(8),
            //         child: CircleAvatar(
            //           backgroundImage: NetworkImage(user.photoUrl),
            //           radius: lerpDouble(imgRadius, 20, progress),
            //         ),
            //       ),
            //     ),
            //
            // ),

        ),
        //Fab
        Positioned(
            left: width - 80,
            top: height - 80,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 300),
              opacity: (progress == 1) ? 1.0 : 0.0,
              child: Container(
                width: 50,
                height: 50,
                decoration: const BoxDecoration(
                    color: Colors.blue, shape: BoxShape.circle),
                child: const Icon(
                  Icons.add_a_photo,
                  size: 25,
                ),
              ),
            )),
      ],
    );
  }

  @override
  double get maxExtent => maxHeight;

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
    // TODO: implement paint
    var linearGradient = LinearGradient(
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
        colors: [Colors.blue.shade800, Colors.red.shade800]
    );
    var paint = Paint()
      ..shader = linearGradient.createShader(Offset.zero & size);
    var center = size;
    canvas.drawCircle(Offset(size.width, size.height), radius, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }

}