//
//  ExchangeTestServiceMock.swift
//  test.orangeTests
//
//  Mock class for ExchangeService
//  Created by Alina Nicorescu on 21/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
/**
  Mock class for the main Exchange Service to be used in testing
*/
class ExchangeServiceMock: ExhangeServiceProtocol {
    
    private var latestExchangeRates: ExchangeRates?
    
    private var historycalExchangeRates: HistoricalExchangeRates? 
    
    private var lError: Error?
    
    private var hError: Error?
    
    init(latestExchangeRates: ExchangeRates, error: Error?) {
        self.latestExchangeRates = latestExchangeRates
        self.lError = error
    }
    
    init(historycalExchangeRates: HistoricalExchangeRates, error: Error?) {
        self.historycalExchangeRates = historycalExchangeRates
        self.hError = error
    }
    
    func getLatestExchangeRates(base: String?, completion: @escaping (ExchangeRates?, Error?) -> Void) {
        completion(latestExchangeRates, lError)
    }
    
    func getHistoricalExchangeRates(base: String?, startAt: Date, endAt: Date, completion: @escaping (HistoricalExchangeRates?, Error?) -> Void) {
        completion(historycalExchangeRates, hError)
    }
    
}
