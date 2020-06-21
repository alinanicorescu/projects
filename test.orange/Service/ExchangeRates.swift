//
//  ExchangeRate.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
/**
    Represents the latest exchange exchange rates data
*/
struct ExchangeRates: Decodable {
    
    //The key represents the currency, e.g."RON"
    var rates : [String : Double]
    
    //The base currency
    var base : String
    
    //The timestamp when the data was actualized in YYY-MM-dd format
    var date : String
}
