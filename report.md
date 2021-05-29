# Minimisation of Average Job Scheduling Turnaround Time
## Jake May - 45436126

### Introduction
The aim of this project as a whole is to create a client that is based on a pre-existing simulator called 'ds-sim'. The goal is to implement a script that can schedule jobs to a server based on several different factors.

Stage 2 of the project revolves around the idea of improving upon an existing algorithm with the goal of achieving one of more of the following goals:
- Minimisation of average turnaround time
- Maximisation of average resource utilisation
- Minimisation of total server rental cost

I have chosen to improve upon the best-fit algorithm with the goal of minimising the average turnaround time for scheduling jobs.

### Problem
Currently, the best-fit algorithm looks at servers and calculates the CPU cores required, against the CPU cores a server has available. However, a flaw in this algorithm is that it doesn't look at the possibility of running multiple jobs simultaneously. 

To fix this, I plan to extend the algorithm by putting functionality in place that will check if any servers are completing any jobs, and assigning jobs to servers capable of running any other jobs at the same time.

### Algorithm


### Implementation


### Evaluation


### Conclusion


### References
1. GitHub Repository
https://github.com/Jakeeem/COMP3100-Assignment-2/