import * as React from 'react';

export default function Summary({summaryData}) {

    return (
        <>  
            <div className="row p-2">
                <div className="col-md-3 p-2">
                    <div className="card">
                        <div className="card-body">
                            <div className="card-title">Total Income</div>
                            <div className="card-text">
                                {summaryData.totalIncome}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-3 p-2">
                    <div className="card">
                        <div className="card-body">
                            <div className="card-title">Total Expense</div>
                            <div className="card-text">
                                {summaryData.totalExpense}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-3 p-2">
                    <div className="card">
                        <div className="card-body">
                            <div className="card-title">Total Debts</div>
                            <div className="card-text">
                                <p>Without Interest: {summaryData.totalDebtsWithoutInterest}</p> 
                                <p>With Interest: {summaryData.totalDebtsWithInterest}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-3 p-2">
                    <div className="card">
                        <div className="card-body">
                            <div className="card-title">Pay Off</div>
                            <div className="card-text">
                                {summaryData.payOff}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )

}