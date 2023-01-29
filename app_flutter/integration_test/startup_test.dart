import 'package:app_flutter/main.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('startup test', (tester) async {
    await binding.watchPerformance(() async {
      await tester.pumpWidget(const MyApp());
      await tester.pumpAndSettle();
      final finder = find.byKey(const Key("Header"));
      expect(finder, findsOneWidget);
    }, reportKey: "startup_timeline");
  });
}
