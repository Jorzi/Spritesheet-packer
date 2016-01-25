
Spritesheet packer
==================


A sprite is a small bitmap image used as a graphical representation for an object in videogames, webpages and other interactive graphical applications.
The drawing pipeline of most GPU:s favour drawing sections of a single large bitmap to using multiple individual small bitmaps due to the computational cost of doing a so-called texture switch.
Additionally some GPU:s still prefer handling bitmaps whose dimensions are powers of two.
A typical manual way of implementing this is to divide the large bitmap into an array of fixed-size tiles, commonly known as a tile set.
This can in the worst case be both constraining and memory inefficient.

The preferred way is therefore to edit sprites as individual bitmap files and combine them using an automated packing algorithm, to ensure optimal use of bitmap space.
The algorithm will choose the minimum power-of-two dimension bitmap able to contain all the sprites and stack the sprites compactly.
The basic implementation will treat each sprite as a non-rotatable rectangle. This is usually the preferred format for sprite sheets since it is compatible with any form of graphical pipeline.
Many game development frameworks also allow possible optimisation by allowing 90 degree rotations. GPU:s also allow the mapping of arbitrary polygons to the bitmap when drawing with hardware acceleration.
This is known as texture mapping and can further reduce memory usage by eliminating transparent areas from the sprite.

The primary goal of this project is to create a command-line application that reads all bitmap image files (using built-in libraries for png) in a folder, outputting a single large bitmap file as well as a text document containing the mappings of each individual image. Several packing algorithms will be tested and the application will provide performance metrics, including minimum bounding rectangle, percentage of unused pixel space and computational time.
Additional possible features include (depending on available time):
  * Possibility of choosing square, rectangle and non-power-of-two dimensions for the sprite sheet
  * Cropping of transparent pixels on sprites, providing optimal bounding rectangles
  * Possibility of allowing 90 degree rotations
  * Automatically generated convex polygons (Very advanced feature)