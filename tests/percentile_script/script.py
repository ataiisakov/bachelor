import os
import time
import json

dir_name = 'list_scroll/flutter'
percentiles = [50, 90, 95, 99]


def find_percentile_in_millis(frames: list[int], p: int):
    index = round((len(frames) - 1) * (p / 100))
    return frames[index] / 1000


def get_average_percentile(p: list[float]):
    return sum(p) / len(p)


def main():
    files = os.listdir(dir_name)
    print("\t\t", end='')
    for p in percentiles:
        print(p, end="\t\t")
    print()

    averages = list()
    all_percentiles = list()
    test_num = 1
    # read files from directory
    for filename in files:
        filepath = os.path.join(dir_name, filename)
        print(f"Test#{test_num}:", end=" ")
        test_num += 1
        if os.path.isfile(filepath):
            with open(filepath, 'r') as file:
                try:
                    # parse json and calculate percentiles [50,90,95,99]
                    jsondata = json.loads(file.read())
                    key1 = 'scrolling_performance'
                    if key1 in jsondata:
                        jsonobject = jsondata[key1]
                        key2 = 'frame_rasterizer_times'
                        if key2 in jsonobject:
                            frames = jsonobject[key2]
                            curr_percentile = list()
                            frames.sort()

                            for p in percentiles:
                                percentile = find_percentile_in_millis(frames, p)
                                print(percentile, end="\t")
                                curr_percentile.append(percentile)

                            all_percentiles.append(curr_percentile)
                            print("\n")
                except json.JSONDecodeError as e:
                    print(e)

    # calculate averages from tests for each percentile
    for i in range(len(percentiles)):
        percentile_average = list()
        for j in range(len(all_percentiles)):
            percentile_average.append(all_percentiles[j][i])
        averages.append(get_average_percentile(percentile_average))

    print("Averages from tests:")
    for p in percentiles:
        print(p, end="\t\t")
    print()
    rounded_averages = [round(avg, 2) for avg in averages]
    for avg in rounded_averages:
        print(avg, end="\t")


if __name__ == '__main__':
    main()
