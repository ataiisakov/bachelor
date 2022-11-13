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

class _DetailScreenState extends State<DetailScreen> {
  late ScrollController _scrollController;

  double _bgHeight = 265;
  double _imageRadius = 50.0;
  double _textScale = 1.0;
  double progress = 0.0;
  double _imgX = 0;
  double _imgY = 0;
  double _textX = 0;
  double _textY = 0;
  double _opacity = 0;

  @override
  void initState() {
    super.initState();

    _scrollController = ScrollController()
        ..addListener(() {
          progress = min(1.0, _scrollController.offset.toDouble() / _bgHeight);
          setState(() {
              _bgHeight = lerpDouble(200.0, 60.0, progress)!;
              _textScale = lerpDouble(1.0, 0.75, progress)!;
              _imageRadius = lerpDouble(50.0, 20.0, progress)!;
              if(progress == 1) {
                _opacity = 1;
              } else {
                _opacity = 0;
              }
          });
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(body: _buildProfile(context));
  }

  Widget _buildProfile(BuildContext context) {
    var size = MediaQuery.of(context).size;
    return Scaffold(
      body: Container(
          width: double.infinity,
          alignment: Alignment.center,
          child: Column(
              children: [
                _buildProfileHeader(context),
                _buildProfileBody(),
                AnimatedOpacity(
                  duration: Duration(milliseconds: 500),
                  opacity: _opacity,
                  child: Positioned(
                        left:   (size.width - 50.0),
                        bottom: (size.height - 50.0),
                        child: SizedBox(
                          width: 20,
                          height: 20,
                          child: Icon(
                            Icons.camera
                          ),
                        ),
                  ),
                )
              ]
          )
      ),
    );
  }

  Widget _buildProfileHeader(BuildContext context) {
    var screenSize = MediaQuery.of(context).size;
    return SizedBox(
      height: _bgHeight,
      child: Stack(
        clipBehavior: Clip.none,
        children: <Widget>[
          CustomPaint(
            painter: ProfileBackground(),
            size: Size(screenSize.width, _bgHeight),
          ),
          Positioned(
              left: screenSize.width / 2 - _imageRadius,
              top: _bgHeight * 0.5,
              child: Column(
                children: [
                  // Text(
                  //   widget.user.name,
                  //   style: const TextStyle(
                  //       fontWeight: FontWeight.bold, fontSize: 33),
                  //   textScaleFactor: _textScale,
                  // ),
                  Container(height: 15),
                  CircleAvatar(
                    backgroundImage: NetworkImage(widget.user.photoUrl),
                    radius: _imageRadius,
                  )
                ],
              )
          )
        ],
      ),
    );
  }

  Widget _buildProfileBody() {
    return Expanded(
        child: SingleChildScrollView(
          controller: _scrollController,
          scrollDirection: Axis.vertical,
            child: Container(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 50),
              child: Text(lorem(paragraphs: 4, words: 500)),
            ),
        )
    );
  }
}
