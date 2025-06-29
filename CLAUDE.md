# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

JWave is a Java library for wavelet transforms, implementing Discrete Fourier Transform (DFT), Fast Wavelet Transform (FWT), and Wavelet Packet Transform (WPT) algorithms in 1-D, 2-D, and 3-D. The library provides 50+ wavelets including Haar, Daubechies, Symlets, Coiflets, and Biorthogonal families.

## Development Commands

### Build and Test
```bash
# Build the project
mvn package

# Build without tests
mvn -B package -DskipTests

# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=CompressorTest

# Run a specific test method
mvn test -Dtest=CompressorTest#testCompressionRatios

# Clean and rebuild
mvn clean package
```

### Running the Main Application
```bash
# Run JWave console application (after building)
java -cp target/jwave-1.0.6.jar jwave.JWave
```

## Architecture Overview

### Core Design Patterns
- **Factory Pattern**: `TransformBuilder` and `WaveletBuilder` create transforms and wavelets
- **Strategy Pattern**: Different transform algorithms (DFT, FWT, WPT) implement `BasicTransform`
- **Template Method**: `Wavelet` base class with specific implementations
- **Decorator Pattern**: `AncientEgyptianDecomposition` wraps transforms to handle arbitrary-length data

### Key Components

1. **Transform Entry Points**:
   - `Transform.java`: Main API class wrapping all transform types
   - `TransformBuilder.java`: Factory for creating transforms

2. **Transform Algorithms** (`jwave.transforms`):
   - `DiscreteFourierTransform`: DFT implementation
   - `FastWaveletTransform`: Standard FWT (requires data length 2^n)
   - `WaveletPacketTransform`: WPT/WPD implementation
   - `AncientEgyptianDecomposition`: Wrapper for handling arbitrary-length data
   - `MODWTTransform`: Maximal Overlap Discrete Wavelet Transform
   - `ShiftingWaveletTransform`: Shifts wavelets across data

3. **Wavelet Families** (`jwave.transforms.wavelets`):
   - Base: `Wavelet.java`, `WaveletBuilder.java`
   - Implementations organized by family: `haar/`, `daubechies/`, `symlets/`, `coiflet/`, `biorthogonal/`, etc.

### Important Usage Notes

1. **Arbitrary Length Data**: Most transforms require data length of 2^n. Use `AncientEgyptianDecomposition` wrapper for arbitrary lengths:
   ```java
   Transform t = new Transform(
     new AncientEgyptianDecomposition(
       new FastWaveletTransform(new Haar1())));
   ```

2. **Multi-dimensional Support**: All transforms work with 1-D arrays, 2-D matrices, and 3-D spaces through method overloading in `Transform.java`.

3. **Data Types**: Complex number support available in `jwave.datatypes.natives`.

### Testing Strategy
- JUnit 4.13.2 for unit tests
- Test files mirror source structure in `src/test/java/jwave/`
- Key test classes:
  - `GeneralTest.java`: General functionality
  - `CompressorTest.java`: Compression algorithms (98%+ compression rates)
  - Transform-specific tests in respective packages

### Common Development Tasks

When implementing new wavelets:
1. Extend `Wavelet` class
2. Implement `forward()` and `reverse()` methods with orthogonal/orthonormal coefficients
3. Add to appropriate package under `jwave.transforms.wavelets`
4. Create corresponding unit tests
5. Register in `WaveletBuilder` if needed

When working with transforms:
1. Check data length requirements (2^n for most transforms)
2. Use `AncientEgyptianDecomposition` for arbitrary lengths
3. Results may differ from other implementations due to different orthogonal base construction (this is mathematically correct)