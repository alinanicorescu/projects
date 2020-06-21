//
//  ExchangeCellTableViewCell.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import UIKit

class HomeCell: UITableViewCell {
   
    @IBOutlet weak var rateLabel: UILabel!
    @IBOutlet weak var rateValue: UILabel!
    
    static let REUSE_ID = "HomeCell"
    
    public func configure(_ rate: Rate) {
        rateLabel.text = rate.base
        rateValue.text = rate.value
    }
}
