//
//  SettingsViewController.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import UIKit

/**
    View Controller to display the Settings Screen
*/
class SettingsViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    private let cellId = SettingsCell.REUSE_ID
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let ratesNib = UINib(nibName: cellId, bundle: nil)
        tableView.register(ratesNib, forCellReuseIdentifier: cellId)
      
        tableView.dataSource = self
        tableView.delegate = self
        tableView.reloadData()
    }
}

extension SettingsViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellId, for: indexPath) as? SettingsCell else {
            return UITableViewCell()
        }
        switch indexPath.row {
        case 0:
            cell.configure(SettingsCellViewModel.createRateViewModel())
        case 1:
            cell.configure(SettingsCellViewModel.createRefreshTimeViewModel())
        default:
            return cell
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        return UIView()
    }
}
