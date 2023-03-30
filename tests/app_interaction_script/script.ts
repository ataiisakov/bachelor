import { AppiumDriver } from "@bam.tech/appium-helper";
import { TestCase, measurePerformance } from "@perf-profiler/e2e";

const bundleId = "com.example.xml";
const appActivity = "com.example.xml.MainActivity";
// const bundleId = "com.example.compose";
// const appActivity = "com.example.compose.MainActivity";
// const bundleId = "com.example.app_flutter.host";
// const appActivity = "com.example.app_flutter.host.MainActivity";

const runTest = async () => {
  const driver = await AppiumDriver.create({
    appPackage: bundleId,
    appActivity,
  });

  const testCase: TestCase = {
    beforeTest: async () => {
      driver.stopApp();
    },
    run: async () => {
      // run is where measuring will happen, insert e2e logic here
      driver.startApp();
      // scroll down 3 times
      await driver.findElementByText("Header Text");
      await driver.scrollDown();
      for(var i = 0; i < 3; i++) {
          await driver.scrollDown();
      }
      // scroll up 3 times
      const scrollUp = `new UiScrollable(new UiSelector().scrollable(true)).scrollBackward(${5})`;
      await driver.runUIAutomatorCommand(scrollUp);
      for(var i = 0; i < 3; i++) {
          await driver.runUIAutomatorCommand(scrollUp);
      }
      // go to detail screen
      await driver.clickElementByText("1");
      // wait 2 sec
      await driver.wait(2000)
      // down up scroll
      await driver.scrollDown();
      await driver.runUIAutomatorCommand(scrollUp);
    },
    // Duration is optional, but helps in getting consistent measures.
    // Measures will be taken for this duration, regardless of test duration
    duration: 10000,
  };

  const { writeResults } = await measurePerformance(bundleId, testCase);
  writeResults();
};

runTest();