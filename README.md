# Evolution #

Visualization of the genetic algorithm. Based on JavaFX.

Algorithm looking for a maximum of selected function by the iterative process consisting of:
* calculation of function values for certain function points (individuals)
* selection individuals for the creation of next generation (higher fitness function increase the probability of individuals choose)
* creation of next generation by gene crossover of two selected parents
* applied random gene mutations

## Features ##
* 5 functions with one or more local maxima
* random selection of the first generation of individuals
* population of 20 individuals
* genes represented as an array of bits
* used crossover recombination algorithm
* algorithm resistant to noise

## Result
![ screen shot ]( http://nano-code.eu/wp-content/uploads/2020/02/EvolutionApp_screan-shot.png )