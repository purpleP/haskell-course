import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import sys
import os


def plot_data(input_file):
    label = os.path.basename(input_file)
    data = np.genfromtxt(input_file, delimiter='\t')
    print(data.shape)
    array_sizes = data[:,0]
    running_times = data[:,1:]
    minimums = running_times.min(axis=1)
    maximums = running_times.max(axis=1)
    means = running_times.mean(axis=1)
    errors = np.array([means - minimums, maximums - means])
    plt.errorbar(array_sizes, means, yerr=errors, label=label)
    plt.xscale('log')
    plt.yscale('log')


matplotlib.use('tkagg')
files = sys.argv[1:]
for f in files:
    plot_data(f)
plt.title('MinMax algorithm comparison')
plt.legend()
plt.show()
