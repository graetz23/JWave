# JWave
## What is it and what it is not?!
Java implementation of a discrete fourier transform (DFT) algorithm, a fast
wavelet transform (FWT) algorithm, and a wavelet packet transform (WPT)
algorithm in 1-D, 2-D, and 3-D.

The wavelet transform algorithms FWT & WPT are
using normalized orthogonal (orthonormal) Haar, Coiflet, Daubechies, and
Legendre wavelets as known by the multi-scale analysis. Additionally the
BiOrthogonal wavelet family, Symlets and the Discrete Mayer FIR filter are
available too. At the moment the implemented Battle23, CDF53, and CDF97 are not
available. Those wavelets have an odd number of coefficients and, therefore, do
not match to the FWT & WPT algorithms which are requesting an even number of
filter (scaling & wavelet) coefficients.

However, those two wavelet transform algorithms are an example for one kind of
a wavelet filtering. There are other ways (!) to filter or implement these
algorithms; feel free. ;-) 

For each there is a forward( .. ) and reverse( .. ) method implemented. The
original time series can be reconstructed by the reverse( .. ) methods with
no losses; see examples or JUnit tests! Otherwise if a thresholding is applied
to Hilbert space there is either a denoising or a data compression possible.

There are also new methods beside the old forward( .. ) and reverse( .. ).
There are stepping forward( .. ) and reverse( .. ), that stop at a certain
level, and decompose( .. ) and recompose( .. ), that supports all level of
possible Hilbert spaces by using the stepping methods.

Anyway, the implementation of JWave is based on several Design Patterns and hopefully
appears user-friendly. Have a look at the the JUnit tests or at the Wiki pages
for a detailed usage.
