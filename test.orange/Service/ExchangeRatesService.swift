//
//  ExchangeRatesService.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import Alamofire

/**
  Service interface used for retrieving the exchange data 
*/
protocol ExhangeServiceProtocol: class {
    /**
       Retrieve latest exchange rates from API
       - Parameters:
          - base: the currency base, e.g: EURO
    */
    func getLatestExchangeRates(base: String?, completion: @escaping (ExchangeRates?, Error?) -> Void)
    
    /**
          Retrieve historical exchange rates from API
          - Parameters:
            - base: the currency base, e.g: EURO
            - startAt: the start date f
            - endAt: the end date
       */
    func getHistoricalExchangeRates(base: String?, startAt: Date, endAt: Date, completion: @escaping (HistoricalExchangeRates?, Error?) -> Void)
}

/**
  Service  class used for retrieving the  rates data from backend API via HTTP
*/
class ExchangeRatesService: ExhangeServiceProtocol {

    static let sharedExhangeService = ExchangeRatesService()
    
    private final let baseURLString = "https://api.exchangeratesapi.io"

    private final let latestURLString = "/latest"
    
    private final let historyURLString = "/history"
    
    
    init() {
    }
    
    func getLatestExchangeRates(base: String?, completion: @escaping (ExchangeRates?, Error?) -> Void) {
        guard let url = URL(string: baseURLString + latestURLString ) else {
            return
        }
        let base = base ?? Constants.DEFAULT_BASE_CURRENCY
        let parameters = ["base": base]
        AF.request(url, parameters: parameters)
        .validate()
        .responseDecodable(of: ExchangeRates.self) { (response) in
            switch response.result {
            case .success(let exchangeRate) :
                completion(exchangeRate, nil)
            case .failure(let error) :
                completion(nil, error)
            }
        }
    }
    
    func getHistoricalExchangeRates(base: String?, startAt: Date, endAt: Date, completion: @escaping (HistoricalExchangeRates?, Error?) -> Void) {
        guard let url = URL(string: baseURLString + historyURLString) else {
            return
        }
        guard startAt <= endAt else {
            completion(nil, nil)
            return
        }
        let parameters = ["base": base ?? Constants.DEFAULT_BASE_CURRENCY,
                          "start_at": formatDate(date: startAt),
                          "end_at" : formatDate(date: endAt)]
        AF.request(url, parameters: parameters)
        .validate()
        .responseDecodable(of: HistoricalExchangeRates.self) { (response) in
            switch response.result {
            case .success(let exchangeRates) :
                completion(exchangeRates, nil)
            case .failure(let error) :
                completion(nil, error)
            }
        }
    }
    
    private func formatDate(date: Date) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = Constants.DATE_FORMATTER
        return dateFormatter.string(from: date)
    }
}
