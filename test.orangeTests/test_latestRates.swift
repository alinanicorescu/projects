//
//  test_orangeTests.swift
//  test.orangeTests
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import XCTest
@testable import test_orange


class test_LatestRatesTests: XCTestCase {
        
    var sut: HomeViewModel!
    
    let rates = ["CAD": 1.5209,
    "HKD": 8.6889,
    "ISK": 154.6,
    "PHP": 56.133,
    "DKK": 7.4554,
    "HUF": 345.44,
    "CZK": 26.683,
    "AUD": 1.6261,
    "RON": 4.8428,
    "SEK": 10.567,
    "IDR": 15927.67,
    "INR": 85.352,
    "BRL": 6.0029,
    "RUB": 77.6565,
    "HRK": 7.5665,
    "JPY": 119.77,
    "THB": 34.765,
    "CHF": 1.0656,
    "SGD": 1.5623,
    "PLN": 4.4516,
    "BGN": 1.9558,
    "TRY": 7.6887,
    "CNY": 7.9332,
    "NOK": 10.7135,
    "NZD": 1.7403,
    "ZAR": 19.444,
    "USD": 1.121,
    "MXN": 25.3126,
    "ILS": 3.862,
    "GBP": 0.90505,
    "KRW": 1353.53,
    "MYR": 4.7854]
    
    private let refreshTimestamp = "2020-06-19"
    private var ratesCalled: Bool = false
    private var errorHandlerCalled: Bool = false
    
    override func setUp() {
        super.setUp()
        let base = UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY)
        ?? Constants.DEFAULT_BASE
        let latestRates = ExchangeRates(rates: rates, base: base, date: refreshTimestamp)
        
        let mockService = ExchangeTestServiceMock(latestExchangeRates: latestRates, error: nil)
        
        sut = HomeViewModel(service: mockService,
                            exchangeRatesChanged: self.ratesChanged, errorHandler: self.errorHandler(error:))
    }
    
    private func ratesChanged() {
        ratesCalled = true
    }
    
    private func errorHandler(error: Error?) {
        errorHandlerCalled = true
    }

    override func tearDown() {
        ratesCalled = false
        errorHandlerCalled = false
        sut = nil
        super.tearDown()
    }
    
    func testRatesOK() {
        sut.fetchExchangeRates()
        XCTAssertEqual(sut.getNumberOfRates(), 32, "Number of rates not OK")
        XCTAssertEqual(ratesCalled, true, "Rates callback was not called")
    }
    
    func testCurrencyBaseOK() {
        sut.fetchExchangeRates()
        let base = UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY)
        ?? Constants.DEFAULT_BASE
        XCTAssertEqual(sut.getBaseCurrency(), base)
    }
    
    func testDateOK() {
        sut.fetchExchangeRates()
        XCTAssertEqual(sut.getRefreshTimestamp(), self.refreshTimestamp, "Refresh timestamp not OK")
    }
    
    func testRatesFail() {
        XCTAssertNoThrow(sut.fetchExchangeRates())
        XCTAssertNotEqual(self.errorHandlerCalled, true, "Exception handler was not called")

    }

}
