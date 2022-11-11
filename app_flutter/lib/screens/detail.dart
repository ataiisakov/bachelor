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

class _DetailScreenState extends State<DetailScreen>
    with SingleTickerProviderStateMixin {
  late ScrollController _scrollController;
  late AnimationController _controller;

  late Animation<double> _bgHeightAnim;
  late Animation<double> _textScaleAnim;
  late Animation<double> _imgSizeAnim;
  late Animation<double> _imgPosAnim;

  double _startBackgoundHeight = 200;
  double _endBackgroundHeight = 60;
  double _imageRadius = 50;
  double _profileHeaderHeight = 265;
  double _targetProfileHeight = 80;

  @override
  void initState() {
    super.initState();

    _controller = AnimationController(vsync: this);
    _scrollController = ScrollController()
      ..addListener(() {
        var progress = min(
            1.0,
            _scrollController.offset.toDouble() /
                (_bgHeightAnim.value.toDouble()));
        setState(() {
          _controller.value = progress;
        });
      });

    _bgHeightAnim = Tween(begin: 200.0, end: 60.0).animate(_controller);
    _textScaleAnim = Tween(begin: 1.0, end: 0.75).animate(_controller);
    _imgSizeAnim = Tween(begin: 50.0, end: 20.0).animate(_controller);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(body: _buildProfile(context));
  }

  Widget _buildProfile(BuildContext context) {
    return Scaffold(
      body: Container(
          width: double.infinity,
          alignment: Alignment.center,
          child: Column(
              children: [_buildProfileHeader(context), _buildProfileBody()])),
    );
  }

  Widget _buildProfileHeader(BuildContext context) {
    var screenSize = MediaQuery.of(context).size;
    return SizedBox(
      height: _bgHeightAnim.value,
      child: Stack(
        children: <Widget>[
          CustomPaint(
            painter: ProfileBackground(),
            size: Size(screenSize.width, _startBackgoundHeight),
          ),
          Positioned(
              left: screenSize.width / 2 - _imageRadius,
              top: _startBackgoundHeight - 100,
              child: Column(
                children: [
                  Text(
                    widget.user.name,
                    style: const TextStyle(
                        fontWeight: FontWeight.bold, fontSize: 33),
                    textScaleFactor: _textScaleAnim.value,
                  ),
                  Container(height: 15),
                  CircleAvatar(
                    backgroundImage: NetworkImage(widget.user.photoUrl),
                    radius: _imgSizeAnim.value,
                  )
                ],
              ))
        ],
      ),
    );
  }

  Widget _buildProfileBody() {
    return Expanded(
        child: SingleChildScrollView(
      scrollDirection: Axis.vertical,
      child: Container(
        padding:
            const EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 50),
        child: Text(lorem(paragraphs: 4, words: 500)),
      ),
    ));
  }
}
