import os
from typing import Dict
import matplotlib.pyplot as plt
import pandas as pd
from os import listdir

from sympy import comp

folderPrefix = ["statistics/", "../"][0]
plotFolder = "plots/"
trendGraphic = False


def savePlot(plot: plt, path: str):
    plot.savefig(path, bbox_inches='tight', facecolor="white")


def show(plt: plt):
    plt.show()


def csvToDf(csvPath):
    return pd.read_csv(csvPath, header=0)


def allCsvNameInDirectory():
    return [i for i in listdir(folderPrefix) if i.endswith(".csv")]


def plotCsv(df: pd.DataFrame, comparator: int, table: dict, fName: str):
    fig, ax = plt.subplots()
    ax.set_title(f'Comparing {table["comp"][comparator]}')
    # colors = ['m', 'g', 'b', 'r', 'y', 'c', 'k',]
    # lineStyle = ["-", "--", ":", "-.", '']

    for pos, val in enumerate(table["algo"]):
        x, y = sorted(set(df.iloc[:, 1])), [table['algo'][val][j][comparator]
                                            for j in range(len(table['algo'][val]))]

        print(f"{x}, {y}")
        ax.plot(x, y,
                # color=colors[pos],
                label=val)
    ax.set_xlabel("n")
    ax.set_ylabel(table['comp'][comparator])
    leg = ax.legend()
    if not (os.path.exists(folderPrefix)):
        os.mkdir(folderPrefix)
    filePath = folderPrefix + fName[:-4] + "/"
    if not os.path.exists(filePath):
        os.mkdir(filePath)
    savePlot(fig, filePath + table['comp'][comparator])
    # show(fig)
    return fig


def plotAllCsv():
    allCsv = allCsvNameInDirectory()
    print("These are allCSV", allCsv)
    for fileName in allCsv:
        print(fileName)
        df = csvToDf(folderPrefix + fileName)
        infos = {"algo": dict(), "comp": list()}
        for (pos, col) in enumerate(df.columns):
            if pos < 2:
                continue
            if (col.strip() not in infos['comp']):
                infos["comp"].append(col.strip())
        for index, row in df.iterrows():
            l = list(map(lambda e: str(e).strip(), row.to_list()))
            queue = list(map(float, l[2:]))
            if l[0] in infos["algo"]:
                infos["algo"][l[0]].append(queue)
            else:
                infos["algo"][l[0]] = [queue]
        # print(infos)
        for pos in range(0, len(infos["comp"])):
            fig = plotCsv(df, pos, infos, fileName)
            # return fig


a = plotAllCsv()
