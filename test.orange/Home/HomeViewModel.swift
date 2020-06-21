//
//  HomeViewModel.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation

/**
    Object used to represent  an exchange rate value  to be displayed on the UI
*/
struct Rate : Equatable {
    var base : String
    var value : String
}

/**
    Protocol to define the view model for latest exchange rates
*/
protocol HomeViewModelProtocol: class {
    
    /**
        Get the last timestamp when the rates data was actualized
    */
    func getRefreshTimestamp() -> String
    /**
        Get the base currency, e.g."RON"
    */
    func getBaseCurrency() -> String
    /**
     Get the total number of rates
    */
    func getNumberOfRates() -> Int
    /**
     Get rate data for a specific row
    */
    func getRateData(index: Int) -> Rate
    /**
    Start fetching the rates data at a fixed time interval
    */
    func startFetching()
    /**
     Stop fetching the rates data at a fixed time interval
    */
    func stopFetching()
}


class HomeViewModel {
    
    private var base: String {
        return UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY)
            ?? Constants.DEFAULT_BASE_CURRENCY
    }
    
    var refreshDate: String = ""
        
    var exchangeRates: [Rate] = []
  
    private var exchangeRatesChanged: (() -> Void)?

    private var errorHandler: ((Error) -> Void)?
    
    private var timer: Timer?

    private var reqInProgress: Bool = false
    
    private let service: ExhangeServiceProtocol
    
    init(service: ExhangeServiceProtocol = ExchangeRatesService.sharedExhangeService, exchangeRatesChanged: (() -> Void)?, errorHandler: ((Error) -> Void)?) {
        self.service = service
        self.exchangeRatesChanged = exchangeRatesChanged
        self.errorHandler = errorHandler
        fetchExchangeRates()
    }
    
    func getNumberOfRates() -> Int {
        return exchangeRates.count
    }
    
    func getRateData(index: Int) -> Rate  {
        return exchangeRates[index]
    }
    
    func getBaseCurrency() -> String {
        return base
    }
    
    func getRefreshTimestamp() -> String {
        return refreshDate
    }
    
    func startFetching() {
        timer = Timer.scheduledTimer(timeInterval: TimeInterval(getRefreshRate()), target: self, selector: #selector(fireTimer), userInfo: nil, repeats: true)
    }
    
    func stopFetching() {
        reqInProgress = false
        timer?.invalidate()
    }
    
    @objc func fireTimer() {
        guard  !reqInProgress else {
            return
        }
        reqInProgress = true
        fetchExchangeRates()
    }
    
    
    private func getRefreshRate() -> Int {
        let refreshTime = UserDefaults.standard.integer(forKey: Constants.USER_DEFAULT_REFRESH_TIME_KEY)
        return refreshTime > 0 ? refreshTime : Constants.DEFAULT_REFRESH_INTERVAL
    }
    
    func fetchExchangeRates() {
        service.getLatestExchangeRates (base: self.base){ [weak self] exchangeRates, error in
                if let error = error {
                    self?.exchangeRates = []
                    self?.errorHandler?(error)
                } else {
                    var ratesArr : [Rate] = []
                    if let exchangeRates = exchangeRates {
                        for rate in exchangeRates.rates {
                            ratesArr.append(Rate(base: rate.key, value: String(rate.value)))
                        }
                    }
                    ratesArr.sort(by: {$0.base < $1.base})
                    self?.refreshDate = exchangeRates?.date ?? ""
                    self?.exchangeRates = ratesArr
                    self?.exchangeRatesChanged?()
                    self?.reqInProgress = false
                }
            }
        }
}
