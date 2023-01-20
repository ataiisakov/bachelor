import 'package:app_flutter/main.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  var binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('startup test', (WidgetTester tester) async {
    await binding.watchPerformance(() async {
      await tester.pumpWidget(MyApp());
      await tester.pumpAndSettle();
      await tester.pump(Duration(seconds: 2));
    }, reportKey: "startup_summary");
  });
}
