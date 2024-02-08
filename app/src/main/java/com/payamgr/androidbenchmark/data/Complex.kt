package com.payamgr.androidbenchmark.data

/**
 * Complex number tool.
 * @property x The real value
 * @property y The imaginary value
 * @property precision The string format precision
 */
abstract class Complex(val x: Float, val y: Float) {
    abstract var precision: Int

    companion object {
        /**
         * It makes [Complex] number from the given polar coordination.
         * @param magnitude The magnitude of the number
         * @param phase The phase of the number
         * @return [Complex]
         */
        fun polar(magnitude: Float, phase: Float): Complex = ComplexImpl.polar(magnitude, phase)
    }
}
