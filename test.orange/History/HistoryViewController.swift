//
//  HistoryViewController.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import UIKit
import Charts
import TinyConstraints

/**
    View Controller to display the Historical Exchange Rates - History Tab
*/
class HistoryViewController: UIViewController, ChartViewDelegate {
    
    @IBOutlet weak var usdView: UIView!
    @IBOutlet weak var bgnView: UIView!
    @IBOutlet weak var ronView: UIView!
    
    private var viewModel: HistoryViewModel?

    lazy var ronChart : LineChartView = {
        let chartView = HistoryRatesChartFactory.instance.createChart(xAxisLineColor: .black)
        return chartView
    }()
    
    var usdChart: LineChartView = {
        let chartView = HistoryRatesChartFactory.instance.createChart(xAxisLineColor: .black)
        return chartView
    }()
    
    var bgnChart: LineChartView = {
        let chartView = HistoryRatesChartFactory.instance.createChart(xAxisLineColor: .black)
        return chartView
    }()
        
    override func viewDidLoad() {
        super.viewDidLoad()
        addRONChart()
        addUSDChart()
        addBGNChart()
        viewModel = HistoryViewModel(ratesChanged: createorUpdateCharts, errorHandler: showError(_:))
        viewModel?.fetchHistoricalRates()
    }

    public func addRONChart() {
        ronView.addSubview(ronChart)
        ronChart.edgesToSuperview()
    }
    
    public func addUSDChart() {
        usdView.addSubview(usdChart)
        usdChart.edgesToSuperview()
    }
    
    public func addBGNChart() {
        bgnView.addSubview(bgnChart)
        bgnChart.edgesToSuperview()
    }
    
    public func showError(_ error: Error) {
        Common.presentErrorAlert(error: error, fromVC: self)
    }
        
    public func createorUpdateCharts() {
        let ronData = viewModel?.getRonRates() ?? []
        let usdData = viewModel?.getUsdRates() ?? []
        let bgnData = viewModel?.getBgnRates() ?? []
        
        let ronDataSet = HistoryRatesChartFactory.instance.createDataSet(data: ronData, label: "RON")
        ronChart.data = ronDataSet
        
        let usdDataset = HistoryRatesChartFactory.instance.createDataSet(data: usdData, label: "USD")
        usdChart.data = usdDataset
        
        let bgnDatSet = HistoryRatesChartFactory.instance.createDataSet(data: bgnData, label: "BGN")
        bgnChart.data = bgnDatSet
    }
}
