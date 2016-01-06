[![Build Status](https://travis-ci.org/cscheiblich/JWave.svg?branch=master)](https://travis-ci.org/cscheiblich/JWave) on travis-ci.com
JWave - Open source Java implementation of orthogonal and bi-orthogonal wavelets
===================================

README
------
Java implementation of a discrete fourier transform (DFT) algorithm, a fast wavelet transform (FWT) algorithm, and a wavelet packet transform (WPT) algorithm all in 1-D, 2-D, and 3-D. The wavelet transform algorithms are using normalized orthogonal (orthonormal) Haar, Coiflet, Daubechies, Symlets, and Legendre wavelets but also non orthogonal BiOrthogonal wavelets of different types. The implementation of JWave is based on several Design Patterns and - hopefully - appears user-friendly.

GETTING STARTED
---------------

First of all have a look at the [HowTo](https://github.com/cscheiblich/JWave/wiki/HowTo) page: How JWave is used, or why the result is looking like it does, and especially where the hell you can find an iterative method!? Thanks. :-)

CONTACT
-------

if there are still doubts, try mailing me, otherwise have fun.

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

CHANGELOG
---------
JWave is in version 1.00 20160105:

20160105 version 1.00 :

- added build.xml for using ant
 - set in build.xml your path JUnit4, e.g. /usr/share/java/junit.jar
 - ! to have the build done:
  - ant
  - should work easy peasy
 - ! to have a test run on your system:
  - java -cp ./dist/JWave.jar jwave.JWave Fast Wavelet Transform Daubechies 20
  - java -cp /usr/share/java/junit4.jar:./dist/JWave.jar org.junit.runner.JUnitCore jwave.TransformTest
  - do not forget to change /usr/share/java/junit4.jar to yours
- added README.md
- added LICENSE.md
- updated Copyright to the years 2008-2016
