//
//  HistoryViewModel.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation

/**
    Protocol to define the view model for history exchange rates
*/
protocol HistoryViewModelProtocol: class {
    func fetchHistoricalRates()
    func getRonRates() -> [(String, Double)]
    func getUsdRates() -> [(String, Double)]
    func getBgnRates() -> [(String, Double)]
}

class HistoryViewModel : HistoryViewModelProtocol {
    
    private var ronRates: [(String, Double)] = []
    
    private var usdRates: [(String, Double)] = []
    
    private var bgnRates: [(String, Double)] = []
    
    private var errorHandler: ((Error) -> Void)?
    
    private var ratesChanged: (() -> Void)?
      
    private let service: ExhangeServiceProtocol
    
    init(service: ExhangeServiceProtocol = ExchangeRatesService.sharedExhangeService,
         ratesChanged: (() -> Void)?, errorHandler: ((Error) -> Void)?) {
        self.service = service
        self.ratesChanged = ratesChanged
        self.errorHandler = errorHandler
    }
    
    public func fetchHistoricalRates() {
        let endDate = Date()
        guard let startDate = Calendar.current.date(byAdding: .day, value: -Constants.HISTORY_DAYS_COUNT, to: endDate) else {
            return
        }
        fetchHRates(startDate: startDate, endDate: endDate)
    }
    
   private func resetRates() {
          ronRates = []
          bgnRates = []
          usdRates = []
      }
      
    private func sortRates() {
        ronRates.sort(by: { $0.0 < $1.0})
        bgnRates.sort(by: { $0.0 < $1.0})
        usdRates.sort(by: { $0.0 < $1.0})
    }
    
    func getRonRates() -> [(String, Double)] {
         return ronRates
     }
     
     func getUsdRates() -> [(String, Double)] {
         return usdRates
     }
    
     func getBgnRates() -> [(String, Double)] {
        return bgnRates
    }
       
    private func fetchHRates(startDate: Date, endDate: Date) {
        service.getHistoricalExchangeRates(base: Constants.DEFAULT_BASE_CURRENCY, startAt: startDate, endAt: endDate) {[weak self] hRates, error in
                if let error = error {
                    self?.resetRates()
                    self?.errorHandler?(error)
            } else {
                let rates = hRates?.rates ?? [:]
                for date in rates.keys {
                    if let ratesDict = rates[date] {
                        if let ronRate = ratesDict[Constants.RON_CURRENCY] {
                            self?.ronRates.append((date, ronRate))
                    }
                        if let usdRate = ratesDict[Constants.USD_CURRENCY] {
                        self?.usdRates.append((date, usdRate))
                    }
                        if let bgnRate = ratesDict[Constants.BGN_CURRENCY] {
                        self?.bgnRates.append((date, bgnRate))
                    }
                }
            }
            self?.sortRates()
            self?.ratesChanged?()
        }}
    }
}
