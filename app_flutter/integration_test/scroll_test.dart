import 'package:app_flutter/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized()
      as IntegrationTestWidgetsFlutterBinding;

  // Build our app and trigger a frame.
  testWidgets('Scroll Flutter test', (tester) async {
    await tester.pumpWidget(const MyApp());
    await tester.pumpAndSettle();

    // final listFinder = find.byKey(const ValueKey('listview'));
    final footerFinder = find.byKey(const ValueKey('Footer'));
    final headerFinder = find.byKey(const ValueKey('Header'));

    await tester.scrollUntilVisible(footerFinder, 100000);
    await tester.scrollUntilVisible(headerFinder, -100000);

    await binding.traceAction(
      () async {
        // Scroll until the item to be found appears.
        await tester.scrollUntilVisible(footerFinder, 100000);
        await tester.scrollUntilVisible(headerFinder, -100000);
      },
      reportKey: 'scrolling_timeline',
    );
  });
}
