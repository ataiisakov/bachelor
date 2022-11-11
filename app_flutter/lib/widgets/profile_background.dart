import 'dart:math';

import 'package:flutter/material.dart';

const lightBlue = Color(0xFF1289E8);
const darkBlue = Color(0xFF1575C4);

class ProfileBackground extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    var width = size.width;
    var height = size.height;

    var pathBg = Path()
      ..reset()
      ..moveTo(0, 0)
      ..lineTo(0, height)
      ..lineTo(width, height)
      ..lineTo(width, 0)
      ..close();

    var topCurvedBg = height * 0.2.toDouble();

    var handlePoint = Point((width * 0.25), topCurvedBg);

    var curvedPath = Path()
      ..reset()
      ..moveTo(0, height)
      ..lineTo(width, height)
      ..lineTo(width, topCurvedBg)
      ..quadraticBezierTo(handlePoint.x, handlePoint.y, 0, height)
      ..close();
    var paint = Paint()..color = lightBlue;
    var bgPaint = Paint()..color = darkBlue;

    canvas.drawPath(curvedPath, paint);
    if (height > 100) {
      canvas.drawPath(pathBg, bgPaint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return false;
  }
}
