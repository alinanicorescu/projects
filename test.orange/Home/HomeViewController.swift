//
//  HomeViewController.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import UIKit

/**
    View Controller to display the Latest Exchange Rates - HomeTab
*/
class HomeViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    private var viewModel: HomeViewModel?
    
    @IBOutlet weak var timestampLabel: UILabel!
    @IBOutlet weak var baseLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTableView()
        viewModel = HomeViewModel(exchangeRatesChanged: self.updateUI, errorHandler: self.showError)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        updateBaseAndTime()
        viewModel?.fetchExchangeRates()
        viewModel?.startFetching()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        viewModel?.stopFetching()
    }
    
   
    private func configureTableView() {
        let cellId = HomeCell.REUSE_ID
        let homeNib = UINib(nibName: cellId, bundle: nil)
        tableView.register(homeNib, forCellReuseIdentifier: cellId)
        tableView.dataSource = self
    }
    
    public func updateUI() {
        tableView.reloadData()
        updateBaseAndTime()
    }
    
    private func updateBaseAndTime() {
        guard let viewModel = viewModel else {
            return
        }
        timestampLabel.text = viewModel.getRefreshTimestamp()
        baseLabel.text = viewModel.getBaseCurrency()
    }
    
    public func showError(_ error: Error) {
       Common.presentErrorAlert(error: error, fromVC: self)
    }
    
}

extension HomeViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel?.getNumberOfRates() ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if let cell = tableView.dequeueReusableCell(withIdentifier: HomeCell.REUSE_ID) as? HomeCell {
            if let rate = viewModel?.getRateData(index: indexPath.row) {
                cell.configure(rate)
            }
            return cell
        }
        return UITableViewCell()
    }
    
}
