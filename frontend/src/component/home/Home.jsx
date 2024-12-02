import { useEffect, useState } from "react";
import Api from "../../api/Api";
import Summary from "./Summary";
import { summaryFilterBy } from "../helpers/CommonData";

export default function Home() {

    //summary
    const [summaryData, setSummaryData] = useState([]);
    const [summaryFilter, setSummaryFilter] = useState(1);

    useEffect(() => {
        getSummaryData();
    }, [summaryFilter]);


   
    //summary api
    const getSummaryData = () => {
        Api.get(`/home/summary?filterby=${summaryFilter}`)
            .then(response => {
                console.log(response);
                setSummaryData(response);
            })
            .catch(error => {
                console.log(error);
            })
    }



    return (
        <>
            <div className="summary my-2">
                <div className="d-flex justify-content-end mx-2">
                    <select 
                        onChange={(e)=>{setSummaryFilter(e.target.value)}}
                        style={{ maxWidth: "150px" }} 
                        name="" id="" 
                        className="form-select">
                        {Array.from(summaryFilterBy).map(([key, val]) => (
                            <option key={key} value={key}>{val}</option>
                        ))}
                    </select>
                </div>
                <Summary 
                    summaryData ={summaryData}
                />
            </div>
        </>
    )

}