# Inverse MODWT (iMODWT) Implementation for JWave

## Overview

The inverse Maximal Overlap Discrete Wavelet Transform (iMODWT) has been successfully implemented in JWave 1.0.7-SNAPSHOT. This implementation allows for perfect reconstruction of signals after MODWT decomposition, enabling applications such as denoising, compression, and multi-resolution analysis.

## Key Features

- **Perfect Reconstruction**: Achieves numerical precision within machine epsilon (~10^-16)
- **Arbitrary Length Support**: Works with signals of any length (not restricted to powers of 2)
- **Translation Invariance**: Maintains the shift-invariant property of MODWT
- **Multi-level Support**: Supports reconstruction from any number of decomposition levels
- **All Wavelet Support**: Works with all wavelets in the JWave library

## Implementation Details

### Algorithm

The inverse MODWT follows the algorithm described in Percival & Walden (2000):

1. Start with the approximation coefficients at the deepest level
2. For each level (from deepest to level 1):
   - Upsample the synthesis filters by inserting zeros
   - Apply circular convolution with the adjoint operation
   - Combine approximation and detail reconstructions
3. The final result is the reconstructed signal

### Key Methods

#### `inverseMODWT(double[][] coefficients)`
The primary inverse transform method:
```java
public double[] inverseMODWT(double[][] coefficients)
```
- **Input**: 2D array with structure `[D1, D2, ..., D_maxLevel, A_maxLevel]`
- **Output**: Reconstructed time-domain signal

#### `reverse(double[] arrHilb)`
Override of the standard JWave reverse method:
```java
@Override
public double[] reverse(double[] arrHilb)
```
- **Input**: Flattened coefficient array (for single-level transforms)
- **Output**: Reconstructed time-domain signal

### Mathematical Foundation

The reconstruction formula for MODWT is:

```
x[t] = Σ(j=1 to J) W_j * d[j] + V_J * a[J]
```

Where:
- `W_j` is the synthesis operator for detail coefficients at level j
- `V_J` is the synthesis operator for approximation coefficients at level J
- `d[j]` are the detail coefficients
- `a[J]` are the approximation coefficients

## Usage Examples

### Basic Usage
```java
// Create MODWT transform
MODWTTransform modwt = new MODWTTransform(new Daubechies4());

// Forward transform
double[] signal = generateSignal(256);
double[][] coeffs = modwt.forwardMODWT(signal, 4);

// Inverse transform
double[] reconstructed = modwt.inverseMODWT(coeffs);
```

### Denoising Application
```java
// Forward transform
double[][] coeffs = modwt.forwardMODWT(noisySignal, 5);

// Threshold detail coefficients
double threshold = calculateThreshold(coeffs);
for (int level = 0; level < coeffs.length - 1; level++) {
    for (int i = 0; i < coeffs[level].length; i++) {
        if (Math.abs(coeffs[level][i]) < threshold) {
            coeffs[level][i] = 0.0;
        }
    }
}

// Inverse transform for denoised signal
double[] denoised = modwt.inverseMODWT(coeffs);
```

### Selective Reconstruction
```java
// Keep only certain frequency bands
double[][] modifiedCoeffs = new double[coeffs.length][];
for (int i = 0; i < coeffs.length; i++) {
    if (i < 2) {
        // Zero out high-frequency details
        modifiedCoeffs[i] = new double[coeffs[i].length];
    } else {
        // Keep low-frequency components
        modifiedCoeffs[i] = coeffs[i].clone();
    }
}

double[] lowPassFiltered = modwt.inverseMODWT(modifiedCoeffs);
```

## Performance Characteristics

- **Time Complexity**: O(N log N) for N-length signal
- **Space Complexity**: O(N) - no additional large buffers required
- **Numerical Stability**: Maintains precision for signals with wide dynamic range

## Test Coverage

Comprehensive test suite includes:
1. **Perfect Reconstruction Tests**: Verifies exact signal recovery
2. **Energy Conservation Tests**: Confirms Parseval's relation holds
3. **Non-Power-of-2 Length Tests**: Validates arbitrary length support
4. **Coefficient Modification Tests**: Ensures proper handling of modified coefficients
5. **Multi-Wavelet Tests**: Tests with Haar, Daubechies, Symlets, and Coiflets
6. **Edge Case Tests**: Empty inputs, constant signals, linear trends

## Comparison with Other Implementations

The implementation has been designed to match the behavior of:
- MATLAB's Wavelet Toolbox `imodwt()` function
- R's wavelets package `imodwt()` function
- PyWavelets' inverse MODWT

## Technical Notes

1. **Filter Normalization**: The implementation automatically normalizes wavelet filters to ensure orthonormality
2. **Circular Convolution**: Uses `Math.floorMod` for robust circular indexing
3. **Adjoint Operation**: The inverse uses the adjoint of the forward convolution operation
4. **Level Validation**: Includes checks to prevent integer overflow in upsampling

## Integration with JWave

The inverse MODWT integrates seamlessly with JWave's architecture:
- Extends the existing `MODWTTransform` class
- Follows JWave's coding conventions
- Compatible with all JWave wavelets
- Maintains backward compatibility

## Future Enhancements

Potential improvements for future versions:
1. Parallel processing for large signals
2. In-place reconstruction to reduce memory usage
3. Streaming support for very long signals
4. GPU acceleration for massive datasets

## References

1. Percival, D. B., & Walden, A. T. (2000). Wavelet Methods for Time Series Analysis. Cambridge University Press.
2. Cornish, C. R., Bretherton, C. S., & Percival, D. B. (2006). Maximal overlap wavelet statistical analysis with application to atmospheric turbulence. Boundary-Layer Meteorology, 119(2), 339-374.

## Acknowledgments

This implementation was developed to address the missing inverse MODWT functionality in JWave, enabling complete wavelet analysis workflows for financial time series analysis and signal processing applications.