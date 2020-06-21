//
//  SettingsRateTableViewCell.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import UIKit

class SettingsCell: UITableViewCell {

    @IBOutlet weak var rateTextField: UITextField!
    @IBOutlet weak var label: UILabel!
    private var uiPicker: UIPickerView = UIPickerView()

    public static let REUSE_ID = "SettingsCell"

    private var vm: SettingsCellViewModel?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        configurePicker()
        configureTextField(vm?.getInitialPickerValue() ?? "")
    }
    
    public func configure(_ vm: SettingsCellViewModel) {
        self.vm = vm
        self.label.text = vm.getLabelText()
        configureTextField(vm.getInitialPickerValue())
        configurePicker()
    }
    
    private func configurePicker() {
        uiPicker.backgroundColor = .white
        uiPicker.dataSource = self
        uiPicker.delegate = self
    }
    
    private func configureTextField(_ value: String) {
        rateTextField.text = value
        rateTextField.inputView = uiPicker
        rateTextField.returnKeyType = .done
        
        let toolBar = UIToolbar(frame: CGRect(x:0, y: 0,width:UIScreen.main.bounds.width, height:44))
        toolBar.barStyle = UIBarStyle.default
        let flexBarButton = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItem.Style.plain, target: self, action: #selector(donePicker))
        doneButton.setTitleTextAttributes([.foregroundColor: UIColor.systemOrange], for: .normal)
        toolBar.setItems([flexBarButton, doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        rateTextField.inputAccessoryView = toolBar
      }

      @objc func donePicker() {
          rateTextField.resignFirstResponder()
      }
}

extension SettingsCell: UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return vm?.numberOfPickerVlues() ?? 0
    }
}

extension SettingsCell: UIPickerViewDelegate {
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let rateValue = vm?.titleForPickerValue(index: row)
        rateTextField.text = rateValue
        if let key = vm?.getUserDefKey() {
            UserDefaults.standard.set(rateValue, forKey: key)
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return vm?.titleForPickerValue(index: row)
    }
}
