//
//  RateViewModel.swift
//  test.orange
//
//  Created by Alina Nicorescu on 21/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import UIKit

/**
    View Model Protocol for a settings table cell
*/
protocol SettingsCellViewModelProtocol: class {
    
    func getLabelText() -> String
    func getInitialPickerValue() -> String
    func numberOfPickerVlues() -> Int
    func titleForPickerValue(index: Int) -> String
    func getUserDefKey() -> String?
}

class SettingsCellViewModel: SettingsCellViewModelProtocol {
    
    private var labelText = ""
    
    private var pickerValues: [String] = []
    
    private var initialPickerValue: String
    
    private var userDefKey : String?
    
    
    public static func createRateViewModel() -> SettingsCellViewModel {
        return SettingsCellViewModel(labelText: "Rate", pickerValues: Constants.RATES_VALUES, initialPickerValue: UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY) ?? String(Constants.USER_DEFAULT_RATE_KEY),
                       userDefKey: Constants.USER_DEFAULT_RATE_KEY)
    }
    
    public static func createRefreshTimeViewModel() -> SettingsCellViewModel {
        return SettingsCellViewModel(labelText: "Refresh Time", pickerValues: Constants.REFRESH_INTERVAL_VALUES.map({return String($0)}), initialPickerValue: UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_REFRESH_TIME_KEY) ?? String(Constants.DEFAULT_REFRESH_INTERVAL), userDefKey: Constants.USER_DEFAULT_REFRESH_TIME_KEY)
    }
    
    
    private init(labelText: String, pickerValues: [String],
                 initialPickerValue: String, userDefKey: String) {
        self.labelText = labelText
        self.pickerValues = pickerValues
        self.initialPickerValue = initialPickerValue
        self.userDefKey = userDefKey
    }
    
    func getLabelText() -> String {
        return labelText
    }
    
    func getInitialPickerValue() -> String {
        return initialPickerValue
    }
    
    func getRefreshRateValue() -> String {
       return UserDefaults.standard.string(forKey: Constants.USER_DEFAULT_RATE_KEY) ?? String(Constants.DEFAULT_REFRESH_INTERVAL)
    }
    
    func numberOfPickerVlues() -> Int {
        return pickerValues.count
    }
    
    func titleForPickerValue(index: Int) -> String {
        return pickerValues[index]
    }
    
    func getUserDefKey() -> String? {
          return userDefKey
    }
}
