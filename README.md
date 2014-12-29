JWave
=====

Java implementation of a discrete fourier transform (DFT) algorithm, a fast wavelet transform (FWT) algorithm, and a wavelet packet transform (WPT) algorithm in 1-D, 2-D, and 3-D.

The wavelet transform algorithms FWT & WPT are using normalized orthogonal (orthonormal) Haar, Coiflet, Daubechies, and Legendre wavelets as known by the multi-scale analysis. Additionally the BiOrthogonal wavelet family, Symlets and the Discrete Mayer FIR filter are available too.

At the moment the implemented Battle23, CDF53, and CDF97 are not available. Those wavelets have an odd number of coefficients and, therefore, do not match to the FWT & WPT algorithms which are requesting an even number of filter (scaling & wavelet) coefficents.

However, those two wavelet transform algorithms are an example for one kind of a wavelet filtering. There are other ways (!) to filter or implement these algorithms; feel free. ;-)

Furthermore, there is a new method beside forward( .. ) and reverse( .. ) of the FWT & WPT called decompose( ). This method allows for splitting a 1-D time signal into all possible filter levels or so in each posible wavelet space. The result is 2-D, starting with orignal signal; e.g. matrixResult[0][N]. Therefore, the method decompose( .. ) replaces an often requested stepping method that stops a set level; just take the coeffs from the requested level p of the 2-D result; matrixResult[p][N].

The methods forward( .. ) ande reverse( .. ) are always performing the maximal number of steps of a given signal of length N=2^p | p:={0,1,2,3,..}. Each step or level p is add up to the coefficients form the level p-1 before. So the result is 1-D, which is a (!) wanted - great - advantage of these kind of wavelet algorithms; available due to the transforms are orthogonal (orthonormal). The original time series can be reconstructed by the reverse( .. ) method with no losses; see examples or JUnit tests!

Anyway, the implementation of JWave is based on several Design Patterns and - hopefully - appears user-friendly. Have a look at the Wiki pages or into the JUnit tests for usage.

Christian.
