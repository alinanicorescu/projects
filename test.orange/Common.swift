//
//  Common.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import UIKit

final class Common {
    
    public static func presentErrorAlert(error: Error, fromVC: UIViewController) {
        let alert = UIAlertController(title: "Error", message: "An error occured while fetching data", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        fromVC.present(alert, animated: false, completion: nil)
    }
    
}
