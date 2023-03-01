import 'package:app_flutter/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('App Interaction test', (tester) async {
    await tester.pumpWidget(const MyApp());
    await tester.pumpAndSettle();

    await binding.traceAction(() async {
      // scroll list
      final listFinder = find.byType(Scrollable);
      //down
      await tester.fling(listFinder, const Offset(0, -10000), 10000);
      expect(find.text("Footer"), findsOneWidget);
      //up
      await tester.fling(listFinder, const Offset(0, 10000), 10000);
      expect(find.text("Header"), findsOneWidget);
      // go to detail
      final userFirst = find.byKey(const ValueKey("user_1"));
      await tester.tap(userFirst);
      await tester.pump();
      // scroll detail
      final scrollView = find.byKey(const ValueKey('scrollview'));
      // down
      await tester.fling(scrollView, const Offset(0, -100), 5000);
      await tester.pumpAndSettle();
      // up
      await tester.fling(scrollView, const Offset(0, 100), 5000);
      await tester.pumpAndSettle();
      // go back
      final iconButton = find.byKey(const ValueKey("iconButton"));
      await tester.tap(iconButton);
      await tester.pump();
    }, reportKey: 'app_inter_timeline');
  });
}
