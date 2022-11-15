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

class _DetailScreenState extends State<DetailScreen> {

  @override
  Widget build(BuildContext context) {
    Widget profile = CustomScrollView(
      slivers: [
        SliverPadding(
            sliver: SliverPersistentHeader(
              pinned: true,
              delegate: _SliverAppBarDelegate(
                  minHeight: 80.0, maxHeight: 200.0, user: widget.user),
            ),
            padding: EdgeInsets.only(bottom: 60)),
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
      {required this.minHeight, required this.maxHeight, required this.user});

  final double minHeight;
  final double maxHeight;
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
        CustomPaint(
          painter: ProfileBackground(),
          size: Size(double.infinity, maxHeight),
        ),
        Positioned(
            left: lerpDouble(width / 2 - imgRadius, width - 100, progress),
            top: lerpDouble(maxHeight - imgRadius * 2, minHeight / 2, progress),
            child: Text(
              user.name,
              overflow: TextOverflow.clip,
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
              textScaleFactor: lerpDouble(1, 0.65, progress),
            )),
        Positioned(
            left: lerpDouble(width / 2 - imgRadius, 20, progress),
            top: lerpDouble(maxHeight - imgRadius,
                minHeight / 2 - imgRadius * 0.10, progress),
            child: CircleAvatar(
              backgroundImage: NetworkImage(user.photoUrl),
              radius: lerpDouble(imgRadius, 20, progress),
            )),
        Positioned(
            left: width - 80,
            top: height - 80,
            child: AnimatedOpacity(
              duration: Duration(milliseconds: 300),
              opacity: lerpDouble(0.0, 1.0, progress)!,
              child: Container(
                width: 50,
                height: 50,
                decoration:
                    BoxDecoration(color: Colors.blue, shape: BoxShape.circle),
                child: Icon(
                  Icons.add_a_photo,
                  size: 25,
                ),
              ),
            )),
      ],
    );
  }

  @override
  // TODO: implement maxExtent
  double get maxExtent => maxHeight;

  @override
  // TODO: implement minExtent
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
    // TODO: implement shouldRebuild
    throw UnimplementedError();
  }
}