import 'package:app_flutter/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('Scrolling Flutter test', (tester) async {
    await tester.pumpWidget(const MyApp());

    final listFinder = find.byKey(const ValueKey('listview'));

    await binding.watchPerformance(() async {
      await tester.fling(listFinder, const Offset(0, -10000), 10000);
      expect(find.text("Footer Text"), findsOneWidget);
      await tester.fling(listFinder, const Offset(0, 10000), 10000);
      expect(find.text("Header Text"), findsOneWidget);
    }, reportKey: 'scrolling_performance');
  });
}
