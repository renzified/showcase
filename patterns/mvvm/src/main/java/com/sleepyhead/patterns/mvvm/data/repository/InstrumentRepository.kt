package com.sleepyhead.patterns.mvvm.data.repository

import com.sleepyhead.patterns.mvvm.data.model.Instrument
import kotlinx.coroutines.delay
import kotlin.random.Random

interface InstrumentRepository {
    suspend fun getWatchlist(): List<Instrument>
    suspend fun getInstrument(id: String): Instrument?
}

class InMemoryInstrumentRepository : InstrumentRepository {

    private val seed = listOf(
        Instrument(
            id = "aapl",
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 214.32,
            changePercent = 0.84,
            sector = "Technology",
            description = "Consumer electronics, software, and services.",
        ),
        Instrument(
            id = "msft",
            symbol = "MSFT",
            name = "Microsoft Corp.",
            price = 428.15,
            changePercent = -0.32,
            sector = "Technology",
            description = "Cloud, productivity, and enterprise platforms.",
        ),
        Instrument(
            id = "jpm",
            symbol = "JPM",
            name = "JPMorgan Chase",
            price = 198.40,
            changePercent = 1.12,
            sector = "Financials",
            description = "Global banking and financial services.",
        ),
        Instrument(
            id = "v",
            symbol = "V",
            name = "Visa Inc.",
            price = 279.88,
            changePercent = 0.21,
            sector = "Financials",
            description = "Digital payments network.",
        ),
        Instrument(
            id = "xom",
            symbol = "XOM",
            name = "Exxon Mobil",
            price = 112.05,
            changePercent = -0.67,
            sector = "Energy",
            description = "Integrated oil and gas company.",
        ),
        Instrument(
            id = "unh",
            symbol = "UNH",
            name = "UnitedHealth",
            price = 521.70,
            changePercent = 0.45,
            sector = "Healthcare",
            description = "Health benefits and care delivery.",
        ),
    )

    override suspend fun getWatchlist(): List<Instrument> {
        delay(650)
        return seed.map { instrument ->
            val jitter = Random.nextDouble(-1.5, 1.5)
            instrument.copy(
                price = (instrument.price * (1 + jitter / 100.0)).round2(),
                changePercent = (instrument.changePercent + jitter / 3).round2(),
            )
        }
    }

    override suspend fun getInstrument(id: String): Instrument? {
        delay(400)
        return seed.find { it.id == id }
    }

    private fun Double.round2(): Double = (this * 100).toInt() / 100.0
}
