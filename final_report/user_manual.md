User manual
===========

The sprite sheet packer is a command line application which can be controlled using command line arguments
If it is run without any arguments, it will use the default settings, generating a sprite sheet from all images in the applications's local directory, 
creating a folder called "output" in the local directory where it saves the sprite sheet as a .png file and its coordinates as a text file.

The sprite sheet will be generated using the MaxRects algorithm, with a horizontal resolution of 1024, and the vertical resolution rounded up to the nearest power of two.
To change these settings, the following arguments can be used:

  * source=folder_path specifies the path to the folder containing the sprites
  * output=folder_path specifies the folder in which to save the result
  * width=N specifies a maximum horizontal resolution of N (1024 default)
  * -po2 forces power-of-two dimensions on the output
  * -scanline specifies that the scanline algorithm will be used
  * -guillotine specifies that the guillotine algorithm will be used
  * -maxrects specifies that the maxRects algorithm will be used
  * -sortedmaxrects specifies that the MaxRects will be used with input quads sorted in descending order
  * -outlines makes the program draw quad outlines for debug purposes
  * -benchmark makes the program compare algorithms, generating test output
  
