Model trainer for (Eric Brill tagger)[http://dx.doi.org/10.3115/974499.974526]
==============================================================================

This is the second incantation of this trainer. The first one was slow and 
could not be reworked to be faster as it would consume HUGE amounts of memory.

This is gonna hurt: almost nothing in this implementation is object oriented. 
At some points it borders on assembly programming. This is on purpose: we need
a very compact representation (for the sake of memory consumption AND locality
of reference) for internal state. We're not gonna waste space needlessly
pointing to things.

What to expect: arrays/matrices of primitive types, caches, indices, bit sets,
bit masks, bitwise operations and so on.

What not to expect: levels of indirection, separation of concerns, of 
responsibilities neither, beautifully structured data as high level concepts, 
deep stack calls, convoluted design patterns and thrills alike.

