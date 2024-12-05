import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# Function to combine all result files and save it into one file
def combine_text_files(input_folder, output_file):

    # List of files
    files = []

    # Create list with all filenames
    for filename in os.listdir(input_folder):
        f = os.path.join(input_folder, filename)
        files.append(f)   

    # Sort files by file name
    files.sort()

    # Write all data of files into final output file
    with open(output_file, 'w') as outfile:
        for filename in files:
            with open(filename, 'r') as infile:
                outfile.write(infile.read())
                outfile.write('\n')

# Path of all result files
path = r'C:\Users\external\Desktop\M1S1\COMPLEX\Projet\resultat\resultat3'

# Combine results into one file
combine_text_files(path, 'final_results.txt')

# Load final results data into numpy array
data = np.loadtxt('final_results.txt', delimiter=',')  # Replace ',' with the actual delimiter (e.g., '\t', ' ')

# Get general statistics of each tree size size
statistics = []

# 100 iterations per tree size
for i in range(0,len(data), 100):

    # get all statistics
    mean_nodes = np.mean(data[i:i+100, 0])
    mean_height = np.mean(data[i:i+100, 1])
    var_height = np.var(data[i:i+100, 1])
    mean_time = np.mean(data[i:i+100, 2])
    var_time = np.var(data[i:i+100, 2])
    mean_time2 = np.mean(data[i:i+100, 3])
    var_time2 = np.var(data[i:i+100, 3])

    # Append into statistics list
    statistics.append([mean_nodes, mean_height, var_height, mean_time, var_time, mean_time2, var_time2])


# Convert the list of statistics to a DataFrame
column_names = ['Nodes',  'Height (mean)', 'Height (variance)', 'Time insert (mean)',
                'Time insert (variance)', 'Time supress (mean)', 'Time supress (variance)']

# Get Dataframe with results
df = pd.DataFrame(statistics, columns=column_names)

# Sort values by node size
df = df.sort_values(by='Nodes')

# Create extra columns to measure complexity
df['Log(Nodes)'] = np.log2(df['Nodes'])
df['n*Log(n)'] = df['Nodes']*df['Log(Nodes)'] 

# Save the DataFrame to an Excel file
df.to_excel('final_results_stats.xlsx', index=False)

# Select APA style graphs
plt.style.use('stylelib\\apa.mplstyle')

# Create Figure 1
plt.figure()

# Select x and y variable and make linear fit
x = df['Log(Nodes)']
y = df['Height (mean)']
coefficients = np.polyfit(x, y, 1) 
linear_fit = np.poly1d(coefficients)

# Plot the data and linear fit
plt.plot(x, y, label='Data', color='blue')
plt.plot(x, linear_fit(x), label='Linear relationship', color='red', linestyle='--')

# Add specifics
plt.xlabel('log(n)')
plt.ylabel('Average height')
plt.legend()

# Save figure
plt.savefig(r'figures\Tree height analysis.png', dpi=300, bbox_inches='tight')
plt.close()


# Make figure 2a
plt.figure()

# Insertion time data
x = df['n*Log(n)']
y = df['Time insert (mean)']
plt.plot(x, y, label='Data', color='blue')

# Fit a linear model Insertion
coefficients = np.polyfit(x, y, 1)
linear_fit = np.poly1d(coefficients)
plt.plot(x, linear_fit(x), label='Linear relationship', color='red', linestyle='--')

# Set specifics
plt.xlabel('n*log(n)')
plt.ylabel('Processing time (s)')
plt.xlim(left=0)
plt.ylim(bottom=0)

# Add a legend to the plot
plt.legend()

# Save the plot
plt.savefig(r'figures\Processing_time_Insertion.png', dpi=300, bbox_inches='tight')

# Close the plot
plt.close()


# Plot figure 2b
plt.figure()

# Insertion time data
x = df['n*Log(n)']
y = df['Time supress (mean)']   
plt.plot(x, y, label='Data', color='blue')

# Fit a linear model Insertion
coefficients = np.polyfit(x, y, 1)
linear_fit = np.poly1d(coefficients)
plt.plot(x, linear_fit(x), label='Linear relationship', color='red', linestyle='--')

# Set specifics
plt.xlabel('n*log(n)')
plt.ylabel('Processing time (s)')
plt.xlim(left=0)
plt.ylim(bottom=0)

# Add a legend to the plot
plt.legend()

# Save the plot
plt.savefig(r'figures\Processing_time_suppression.png', dpi=300, bbox_inches='tight')

# Close the plot
plt.close()



