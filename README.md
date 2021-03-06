# README #

### Perfect Phylogeny Constructor and Visualizer ###

Created by Nick Goelz.

------------------------------------------------------------------------------

Project is built using Maven to store dependencies.

To run:
Download full contents of repo to a file
Build using 

    $ mvn package

Run program with

    ./run

This launches server, to interact with it, go to `localhost:4567` in a browser

All phylogenies are rooted on first entered taxa (therefore unrooted)

Matrix values should be entered with space separators and newlines for each taxa
Please do not end with a new line

>> 1 0
>> 0 1

For binary perfect phylogenies, no state trees necessary (just ensure the correct number of states)
Otherwise enter state trees as follows (newline between each tree)

>> A-B
>> B-C

for each connection in a state tree

>> 0-1
1-2

>> 1-0
>> 0-2

Would be two state trees with the corresponding connections.

You can also enter "default <n>" which will set the state tree for every character to a linear graph
0-1-2 ... -n

Finally the 'states' sets the maximum state.

To use the random phylogeny generation tool enter three fields
Time is a sense of evolutionary time, the number of branching events in the tree
States is the highest state that a character can reach
n is the number of speices to put into the matrix M, will be chosen at random
The default state tree and a all-zero root are assumed
