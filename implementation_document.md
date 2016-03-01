Implermentation of a maxRects sprite sheet packer
=================================================


The program is a command line application whose purpose is to convert a series of images (png only for now) into one single image, lining them all up without overlap, while minimizing the needed bounding rectangle.
The problem can be reduced to packing a series of rectangles as compactly as possible. This is essentially a 2-dimensional variant of the bin packing problem, and it is generally referred to as 2d bin packing.
The algorithm generally seen as one of the most space-efficient and consistent is known as the maximium rectangles (aka maxRects) algorithm.


The program implements several bin packing algorithms in a modular way as "QuadPacker" objects by using a common interface to access them.
Images are read from a specified folder and stored in memory. The bounding rectangles of each image are extracted from the image objects and used as input for the packing function.
In order to control the dimensions of the generated sprite sheet, a maximum width is specified.
Most 2d bin packing implementations start with a fixed size sprite sheet (often referred to as the bin), resizing it every time the bin is unable to contain all rectangles, or just assuming that the bin is large enough.
The implementation presented here, on the other hand, uses a bin of (practically) infinite depth, packing downwards from the upper-left corner, then cutting the bin depth to a minimum once all the rectangles are contained within the bin.
This gives the user the possibility to adjust the ratio of width and height, while eliminating the need for any resizing of the bin.


References
----------

Jukka Jyllänki, 2010, A Thousand Ways to Pack the Bin - A Practical Approach to Two-Dimensional Rectangle Bin Packing