//
//  Rates.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation

/**
    Class to represent common constant values
*/
final class Constants {

    public static let DEFAULT_BASE_CURRENCY = "EUR"
    
    public static let RON_CURRENCY = "RON"
    
    public static let USD_CURRENCY = "USD"
    
    public static let BGN_CURRENCY = "BGN"
    
    public static let DEFAULT_REFRESH_INTERVAL = 5

    public static let RATES_VALUES: [String] =
     ["CAD", "HKD", "ISK", "PHP", "DKK", "HUF", "CZK", "AUD", "RON", "SEK", "IDR", "INR", "BRL", "RUB", "HRK", "JPY", "THB", "CHF", "SSGD", "PLN", "BGN", "TRY", "CNY", "NOK", "NZD", "ZAR", "USD", "MXN", "ILS", "GBP", "KRW", "MYR"]

    public static let REFRESH_INTERVAL_VALUES: [Int] = [3, 5, 15]
    
    public static let USER_DEFAULT_RATE_KEY = "Rate"
    
    public static let USER_DEFAULT_REFRESH_TIME_KEY = "Refresh_Time"
    
    public static let HISTORY_DAYS_COUNT = 10
    
    public static let DATE_FORMATTER = "yyyy-MM-dd"

}
