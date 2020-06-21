//
//  HistoryRatesChartFactory.swift
//  test.orange
//
//  Created by Alina Nicorescu on 18/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import Foundation
import Charts
import TinyConstraints

/**
    Factory class to create charts for exchange rates
*/
final class HistoryRatesChartFactory {
    
     static let instance = HistoryRatesChartFactory()
    
    init() {
        
    }
    /**
        Create a linear chart view to display rates data
    */
    public func createChart(xAxisLineColor: UIColor) -> LineChartView {
        let chartView: LineChartView = LineChartView()
        chartView.backgroundColor = xAxisLineColor
        chartView.rightAxis.enabled = false
        let yAxis = chartView.leftAxis
        yAxis.labelFont = .boldSystemFont(ofSize: 12)
        yAxis.setLabelCount(6, force: false)
        yAxis.labelTextColor = .orange
        yAxis.axisLineColor = .orange
        yAxis.labelPosition = .insideChart
    
        chartView.xAxis.labelPosition = .bottom
        chartView.xAxis.labelRotationAngle = 45
        chartView.xAxis.labelFont = .boldSystemFont(ofSize: 10)
        chartView.xAxis.labelTextColor = .orange
        chartView.xAxis.setLabelCount(6, force: false)
        chartView.xAxis.axisLineColor = xAxisLineColor
        chartView.xAxis.valueFormatter = DateAxisFormatter()
        chartView.legend.textColor = .white
        chartView.legend.font = .boldSystemFont(ofSize: 12)
        chartView.animate(xAxisDuration: 2.5)
        return chartView
    }

    /**
        Create a data set for date data to add in a chart view
           - Parameters:
            - data: the rates data  - an array of (date, value)
            - label: the data set name, e.g currency name: "RON"
    */
    public func createDataSet(data: [(String, Double)], label: String) -> LineChartData{
        let yValues: [ChartDataEntry] = getYValues(data)
        let set1 = LineChartDataSet(entries: yValues, label: label)
        set1.mode = .cubicBezier
        set1.lineWidth = 3
        set1.setColor(.orange)
        set1.fill = Fill(color: .white)
        set1.fillAlpha = 0.8
        set1.drawFilledEnabled = true
        set1.drawHorizontalHighlightIndicatorEnabled = false
        set1.highlightColor = .systemRed
        set1.drawCirclesEnabled = false
        let data = LineChartData(dataSet: set1)
        data.setDrawValues(false)
        return data
    }
    
    private func getYValues(_ data: [(String, Double)]) -> [ChartDataEntry] {
        return data.map{return ChartDataEntry(x: doubleForValue($0.0), y: $0.1)}
    }

    func doubleForValue(_ value: String) -> Double {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = Constants.DATE_FORMATTER
        return dateFormatter.date(from: value)?.timeIntervalSince1970 ?? 0
    }

}
    
/**
    Class used to format the X-axis values represented by timestamps
*/
class DateAxisFormatter: IAxisValueFormatter {
    func stringForValue(_ value: Double, axis: AxisBase?) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = Constants.DATE_FORMATTER
        return dateFormatter.string(from: Date(timeIntervalSince1970: value))
    }
}

