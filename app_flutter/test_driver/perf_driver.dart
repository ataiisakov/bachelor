import 'package:flutter_driver/flutter_driver.dart' as driver;
import 'package:integration_test/integration_test_driver.dart';

Future<void> main() {
  return integrationDriver(responseDataCallback: (data) async {
    if (data != null) {
      var timeline;
      var summaryTimeline;

      const startupTimelineKey = 'startup_timeline';
      const scrollingTimelineKey = 'scrolling_timeline';
      const appInteractionTimelineKey = 'app_inter_timeline';
      for (var key in data.keys) {
        switch (key) {
          case startupTimelineKey:
            {
              summaryTimeline = startupTimelineKey;
              timeline = driver.Timeline.fromJson(data[startupTimelineKey]);
              break;
            }
          case scrollingTimelineKey:
            {
              summaryTimeline = scrollingTimelineKey;
              timeline = driver.Timeline.fromJson(data[scrollingTimelineKey]);
              break;
            }
          case appInteractionTimelineKey:
            {
              summaryTimeline = appInteractionTimelineKey;
              timeline =
                  driver.Timeline.fromJson(data[appInteractionTimelineKey]);
              break;
            }
        }
      }

      // Convert the Timeline into a TimelineSummary that's easier to
      // read and understand.
      final summary = driver.TimelineSummary.summarize(timeline);

      // Then, write the entire timeline to disk in a json format.
      // This file can be opened in the Chrome browser's tracing tools
      // found by navigating to chrome://tracing.
      // Optionally, save the summary to disk by setting includeSummary
      // to true
      await summary.writeTimelineToFile(
        summaryTimeline,
        pretty: true,
        includeSummary: true,
      );
    }
  });
}
