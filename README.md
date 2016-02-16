JWave - Open source Java implementation of orthogonal and bi-orthogonal wavelets
===================================

[![Build Status](https://travis-ci.org/cscheiblich/JWave.svg?branch=master)](https://travis-ci.org/cscheiblich/JWave) on travis-ci.com

README
------

Java implementation of a Discrete Fourier Transform (DFT) algorithm, a Fast Wavelet Transform (FWT) algorithm, and a Wavelet Packet Transform (WPT) algorithm, available in 1-D, 2-D, and 3-D. The wavelet transform algorithms are using normalized orthogonal (orthonormal) wavelets like Haar, Coiflet, Daubechies, Symlets, Legendre, and even some Bi-Orthogonal. The implementation of JWave is based on several Design Patterns and - hopefully - appears user-friendly.

GETTING STARTED
---------------

First of all have a look at the [HowTo](https://github.com/cscheiblich/JWave/wiki/HowTo) page: How JWave is used, or why the result is looking like it does, and especially where the hack you can find an iterative (stepping) method!? However, the bare algorithms of JWave do only support data sampled by 2^p | p E N; e.g. 2, 4, 8, 16, .. 128, 256, 512, 1024, .. and so on: Please use the AncientEgyptianDecomposition class for odd samplings (most frequently asked question)! Thanks. :-)

CONTACT
-------

If there are doubts, try mailing me, otherwise have fun with JWave.

LICENSE
-------

JWave is distributed under the MIT License (MIT); this file is part of.

Copyright (c) 2008-2016 Christian Scheiblich (cscheiblich@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

VERSION
-------

JWave is in version 160109.

CHANGE LOG
----------

version 1602xx :
- added a new type of wavelet transform algorithm: Shifting Wavelet Transform.
 - the algorithm shifts a wavelet by smallest length of 2 over the input array,
 - NOT YET - then by the double of 4, 8, 16, .., p-1, p.
 - the reverse transform takes the largest wavelength of p and shifts,
 - NOT YET - then by half of p-1, .., 16, 8, 4, 2.

version 160109 :
- moved the JUnit tests to an own source directory
 - updated the build.xml file
 - automatically run all files with regex \*\*/\*Test\* in build directory
- updated the build.xml - allowing for OS specific builds now
 - set JUnit path OS dependent; MAC, WIN, or GNU/Linux and Unix
 - additionally set for WIN OS org.hamcrest.core JAR

version 160107 :
- added junit test for compressing a sine signal:
 - sine signal of 1024 * 1024 samples by 1024 oscillations
 - calculating the compression rate in percent; e.g. 99.70703125 % by Daubechies 20 wavelet
 - calculating the absolute maximal differences; e.g. 3.086649707542688E-4 by Daubechies 20 wavelet
- added method for calculating compression rate to class Compressor

version 160106 :
- added build.xml for using ant
 - set in build.xml your path JUnit4, e.g. /usr/share/java/junit.jar
 - ! to have the build done:
  - ant
  - should work easy peasy
 - ! for a quick console example:
  - ant run
 - ! for running the junit tests
  - ant test
 - ! to have a run or test on your system:
  - java -cp ./dist/JWave.jar jwave.JWave Fast Wavelet Transform Daubechies 20
  - java -cp /usr/share/java/junit4.jar:./dist/JWave.jar org.junit.runner.JUnitCore jwave.TransformTest
- added automatic build using travis-ci.com
 - ! https://travis-ci.org/cscheiblich/JWave
 - have a look at ./.travis.yml
- added README.md
- added LICENSE.md
- updated Copyright to the years 2008-2016
- fixed bug in JWave.java for calling console example with Haar wavelet
