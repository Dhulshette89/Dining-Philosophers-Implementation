# Dining-Philosophers-Implementation using Monitors and condition variable
The problem can be stated quite simply as follows. Five philosophers are seated around a circular table. 
Each philosopher has a plate of spaghetti. 
The spaghetti is so slippery that a philosopher needs two forks to eat it. Between each pair of plates is one fork. 
The life of a philosopher consists of alternating periods of eating and thinking. (This is something of an abstraction, even for philosophers, but the other activities are irrelevant here.) When a philosopher gets sufficiently hungry, she tries to acquire her left and right forks, one at a time, in either order. 
If successful in acquiring two forks, she eats for a while, then puts down the forks, and continues to think. 
Implementing solution to this using monitors instead of semaphors.
