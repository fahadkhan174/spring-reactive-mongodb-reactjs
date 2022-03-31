import React, { useEffect, useState } from 'react'

const Dashboard = () => {
    const [kayaks, setKayaks] = useState([])

    useEffect(() => {
        let eventSource = new EventSource('http://localhost:8080/kayaks')
        eventSource.onmessage = (event) => {
            console.log(JSON.parse(event.data))
            setKayaks((kayaks) => kayaks.concat(JSON.parse(event.data)))
        }
        eventSource.onerror = (err) => {
            // console.error('EventSource failed:', err)
            eventSource.close()
        }
        return () => {
            eventSource.close()
            console.log('event closed')
        }
    }, [])

    return (
        <div style={{ marginTop: '20px', textAlign: 'center' }}>
            <h1>Dashboard</h1>
            <div style={{ display: 'inline-flex' }}>
                <ul style={{ listStyle: 'none', textAlign: 'left' }}>
                    {kayaks.map((kayak, index) => (
                        <li key={index}>{kayak.name}</li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default Dashboard
