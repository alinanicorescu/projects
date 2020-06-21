//
//  test_orangeTests.swift
//  test.orangeTests
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import XCTest
@testable import test_orange


class test_HistoricalRates: XCTestCase {
        
    var sut: HistoryViewModel!
    
    
    let historyRates = [
        "2020-06-12" : [
            "CAD": 1.5347,
            "HKD": 8.7607,
            "ISK": 152.1,
            "PHP": 56.763,
            "DKK": 7.4551,
            "HUF": 346.0,
            "CZK": 26.698,
            "AUD": 1.6447,
            "RON": 4.8343,
            "SEK": 10.5103,
            "IDR": 16058.24,
            "INR": 85.7875,
            "BRL": 5.7388,
            "RUB": 78.7662,
            "HRK": 7.566,
            "JPY": 121.26,
            "THB": 34.986,
            "CHF": 1.0697,
            "SGD": 1.5718,
            "PLN": 4.4484,
            "BGN": 1.9558,
            "TRY": 7.7224,
            "CNY": 7.999,
            "NOK": 10.8475,
            "NZD": 1.7553,
            "ZAR": 19.2794,
            "USD": 1.1304,
            "MXN": 25.445,
            "ILS": 3.9189,
            "GBP": 0.89653,
            "KRW": 1360.45,
            "MYR": 4.824],
        "2020-06-10": [
            "CAD": 1.5228,
            "HKD": 8.8157,
            "ISK": 150.7,
            "PHP": 56.767,
            "DKK": 7.4553,
            "HUF": 343.13,
            "CZK": 26.609,
            "AUD": 1.622,
            "RON": 4.8349,
            "SEK": 10.4605,
            "IDR": 16022.03,
            "INR": 85.903,
            "BRL": 5.5213,
            "RUB": 78.1468,
            "HRK": 7.569,
            "JPY": 122.16,
            "THB": 35.422,
            "CHF": 1.0762,
            "SGD": 1.5746,
            "PLN": 4.4524,
            "BGN": 1.9558,
            "TRY": 7.7145,
            "CNY": 8.0305,
            "NOK": 10.5383,
            "NZD": 1.7357,
            "ZAR": 18.8376,
            "USD": 1.1375,
            "MXN": 24.8255,
            "ILS": 3.9045,
            "GBP": 0.88963,
            "KRW": 1352.17,
            "MYR": 4.8361
        ],
        "2020-06-09" : [
            "CAD": 1.5126,
            "HKD": 8.746,
            "ISK": 149.5,
            "PHP": 56.301,
            "DKK": 7.4555,
            "HUF": 344.16,
            "CZK": 26.585,
            "AUD": 1.6156,
            "RON": 4.8359,
            "SEK": 10.3978,
            "IDR": 15815.42,
            "INR": 85.241,
            "BRL": 5.5702,
            "RUB": 77.0563,
            "HRK": 7.565,
            "JPY": 123.41,
            "THB": 35.474,
            "CHF": 1.0861,
            "SGD": 1.57,
            "PLN": 4.4333,
            "BGN": 1.9558,
            "TRY": 7.6574,
            "CNY": 7.9836,
            "NOK": 10.4603,
            "NZD": 1.7291,
            "ZAR": 18.9799,
            "USD": 1.1285,
            "MXN": 24.322,
            "ILS": 3.8911,
            "GBP": 0.89173,
            "KRW": 1353.2,
            "MYR": 4.8159
        ]
    ]
    
    private let refreshTimestamp = "2020-06-19"
    private var ratesCalled: Bool = false
    private var errorHandlerCalled: Bool = false
    
    override func setUp() {
        super.setUp()
        let base = UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY)
        ?? Constants.DEFAULT_BASE
        let hRates = HistoricalExchangeRates(rates: historyRates, base: base, start_at: "2020-06-02", end_at: "2020-06-12")
        let mockService = ExchangeTestServiceMock(historycalExchangeRates: hRates, error: nil)
        sut = HistoryViewModel(service: mockService,
                               ratesChanged: self.ratesChanged, errorHandler: self.errorHandler(error:))
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
        sut.fetchHistoricalRates()
        XCTAssertEqual(sut.getBgnRates().contains(where: {$0.0 == "2020-06-09" && $0.1 == 1.9558}), true, "Element not contained in array of BGN rates")
        XCTAssertEqual(sut.getBgnRates().count, 3, "Number of BGN rates not OK")
        
        XCTAssertEqual(sut.getRonRates().contains(where: {$0.0 == "2020-06-10" && $0.1 == 4.8349}), true, "Element not contained in array of RON rates")
        XCTAssertEqual(sut.getRonRates().count, 3, "Number of RON rates not OK")

        XCTAssertEqual(sut.getUsdRates().contains(where: {$0.0 == "2020-06-12" && $0.1 == 1.1304}), true, "Element not contained in array of USD rates")
        XCTAssertEqual(sut.getUsdRates().count, 3, "Number of USD rates not OK")
        
        XCTAssertEqual(ratesCalled, true, "Rates callback was not called")
    }
    
    
    func fetchHandlerCalledOK() {
        sut.fetchHistoricalRates()
        XCTAssertEqual(self.ratesCalled, true)
    }
    
    func resultsSortedOK() {
        sut.fetchHistoricalRates()
        let ronRates = sut.getRonRates()
        XCTAssertEqual(ronRates.first?.0, "2020-06-09","RON rates results not ordered according to date")
        XCTAssertEqual(ronRates.first?.0, "2020-06-10","RON rates results not ordered according to date")
        XCTAssertEqual(ronRates.first?.0, "2020-06-12","RON rates results not ordered according to date")
    }
    
    func testRatesFail() {
        sut.fetchHistoricalRates()
        XCTAssertNoThrow(sut.fetchHistoricalRates())
        XCTAssertNotEqual(self.errorHandlerCalled, true, "Exception handler was not called")
    }
}
