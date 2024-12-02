import { useEffect } from "react";
import Api from "../../api/Api"

export default function Notification(){

    useEffect(()=>{
        getNotification();
    },[])

    const getNotification = () => {
        Api.get("/notification")
            .then(response=>{
                console.log(response);
            })
            .catch(error=>{
                console.log(error);
            })
    }

    return(
        <>

        </>
    )
}