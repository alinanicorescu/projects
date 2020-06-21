//
//  HistoricalExchangeRates.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
/**
    Represents historical exchange rates data, in a time interval [start_date, end_date]
*/
struct HistoricalExchangeRates: Decodable {
    
    //The key represents the currency, e.g."RON"
    var rates : [String : [String: Double]]
    
    //The base currency
    var base : String
    
    //The end timestamp for the historical data in YYY-MM-dd format
    var start_at : String
    
    //The end timestamp for the historical data in YYY-MM-dd format
    var end_at : String
}
