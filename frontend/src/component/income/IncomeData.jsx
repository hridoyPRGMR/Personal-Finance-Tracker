import { useFormik } from "formik";
import * as Yup from 'yup';
import Api from "../../api/Api";
import { useEffect, useState } from "react";
import VisibilityIcon from '@mui/icons-material/Visibility';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import CustomeAlert from '../helpers/CustomeAlert';
import { Form, Modal, Button } from "react-bootstrap";
import { currency } from "../helpers/CommonData";

export default function IncomeData() {

    const [errors, setErrors] = useState({});
    const [alert, setAlert] = useState({ message: '', severity: '' });
    const handleAlertClose = () => {
        setAlert({ message: '', severity: '' });
    };

    const [incomes, setIncomes] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(4); // Number of items per page
    const [totalPages, setTotalPages] = useState(0);

    let [incomeSources, setIncomeSources] = useState([]);

    useEffect(() => {
        getIncomeSources();
    }, []);

    useEffect(() => {
        getIncomeData();
    }, [page, size]);

    const currencyOptions = currency;

    const formik = useFormik({
        initialValues: {
            income: '',
            income_source: '',
            date: '',
            currency: '',
            description: ''
        },
        validationSchema: Yup.object({
            income: Yup.number()
                .positive('Income must be a positive number.')
                .required('Income is required.'),
            income_source: Yup.number()
                .positive('Invalid Income Source')
                .required('Income Source is required.'),
            date: Yup.date().required('Date is required.'),
            currency: Yup.string().required('Currency is required.'), // Added validation for currency
            description: Yup.string()
                .max(250, "Description must be less than 250 characters.")
        }),
        onSubmit: (values, { resetForm }) => {
            const formattedValues = {
                ...values,
                income_source: parseInt(values.income_source, 10)
            };

            Api.post('/income', formattedValues)
                .then(response => {
                    resetForm();
                    setAlert({ message: 'Income saved successfully!', severity: 'success', duration: 3000 })
                })
                .catch(error => {
                    console.log('Error:', error);
                    setErrors({ global: 'An error occurred while saving the income' });
                });
        }
    });

    const getIncomeSources = () => {
        Api.get('/income-sources')
            .then(response => {
                const data = response.map(item => ({
                    key: item.id,
                    val: item.income_source
                }));
                setIncomeSources(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    };


    const getIncomeData = () => {

        Api.get(`/income?page=${page}&size=${size}`)
            .then(response => {
                console.log(response);
                setIncomes(response.content)
                setTotalPages(response.totalPages)
            })
            .catch(error => {
                console.log('Error: '.error);
            })

    }

    const handleNextPage = () => {
        if (page < totalPages - 1) {
            setPage(page + 1);
        }
    }

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(page - 1);
        }
    }


    const [editModal, showEditModal] = useState(false);
    const [editItem, setEditItem] = useState(null);


    const handleEditModal = (item) => {
        console.log(item);
        setEditItem(item);
        showEditModal(!editModal);
    }

    const editModalClose = () => {
        showEditModal(!editModal);
    }

    const handleEditSubmission = () => {

        delete editItem.incomeSource;
        delete editItem.user;

        Api.put('/income', editItem)
            .then(response=>{
                showEditModal(!editModal);
                setAlert({ message: `Income  Updated successfully! Id:${editItem.id}`, severity: 'success', duration: 3000 })
            })
            .catch(error=>{
                console.log('Error Updating Income data: '.error);
                setAlert({ message: `An error occurred while updating the income source. Id:${editItem.id}`, severity: 'error', duration: 3000 });
            })

    }


    //for view Income 

    const [selectedIncome,setSelectedIncome] = useState(null); 

    const handleView = (item) => {
        setSelectedIncome(item);
    }


    // for delete

    const handleDelete = (id) => {

        const isConfirmed = window.confirm('Are you sure you want to delete this item?');

        if(isConfirmed){
            Api.delete(`/income/${id}`)
                .then(response=> {
                    setAlert({ message: `Income  Deleted successfully! Id:${id}`, severity: 'success', duration: 3000 })
                })
                .catch(error => {
                    setAlert({ message: `An error occurred while updating the income source. Id:${id}`, severity: 'error', duration: 3000 });
                })
        }
        
    }


    return (
        <div>
            <div className="row">
                <CustomeAlert
                    message={alert.message}
                    severity={alert.severity}
                    duration={alert.duration}
                    onClose={handleAlertClose}
                />
                <div className="col-md-6 px-2">
                    <div className="shadow p-4 mb-5 bg-body rounded">
                        <form onSubmit={formik.handleSubmit}>
                            <div className="row mb-3">
                                <div className="col-md-6">
                                    <label htmlFor="income" className="form-label">Income</label>
                                    <input
                                        type="number"
                                        name="income"
                                        className="form-control"
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        value={formik.values.income}
                                    />
                                    {formik.touched.income && formik.errors.income && (
                                        <div className="text-danger">{formik.errors.income}</div>
                                    )}
                                </div>
                                <div className="col-md-6">
                                    <label htmlFor="income_source" className="form-label">Income Source</label>
                                    <select
                                        name="income_source"
                                        className="form-select"
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        value={formik.values.income_source}
                                    >
                                        <option value="">Select</option>
                                        {incomeSources.map(item => (
                                            <option key={item.key} value={item.key}>{item.val}</option>
                                        ))}
                                    </select>
                                    {formik.touched.income_source && formik.errors.income_source && (
                                        <div className="text-danger">{formik.errors.income_source}</div>
                                    )}
                                </div>
                            </div>

                            <div className="row mb-3">
                                <div className="col-md-6">
                                    <label htmlFor="currency" className="form-label">Currency</label>
                                    <select
                                        name="currency"
                                        className="form-select"
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        value={formik.values.currency}
                                    >
                                        <option value="">Select</option>
                                        {Array.from(currencyOptions).map(([key, val]) => (
                                            <option key={key} value={key}>{val}</option>
                                        ))}
                                    </select>
                                    {formik.touched.currency && formik.errors.currency && (
                                        <div className="text-danger">{formik.errors.currency}</div>
                                    )}
                                </div>
                                <div className="col-md-6">
                                    <label htmlFor="date" className="form-label">Date</label>
                                    <input
                                        type="date"
                                        name="date"
                                        className="form-control"
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        value={formik.values.date}
                                    />
                                    {formik.touched.date && formik.errors.date && (
                                        <div className="text-danger">{formik.errors.date}</div>
                                    )}
                                </div>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Description</label>
                                <textarea
                                    name="description"
                                    className="form-control"
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    value={formik.values.description}
                                ></textarea>
                                {formik.touched.description && formik.errors.description && (
                                    <div className="text-danger">{formik.errors.description}</div>
                                )}
                            </div>

                            <div className="d-flex justify-content-end">
                                <button type="submit" className="btn btn-primary">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>

                {/* Income Sources Table Section */}
                <div className="col-md-6 px-2">
                    <div className="shadow p-3 mb-5 bg-body rounded">
                        <h6 className="fw-bold text-dark mb-3">Income Sources</h6>

                        <div className="table-responsive shadow-sm rounded">
                            <table className="table table-hover table-striped">
                                <thead className="table-dark">
                                    <tr className="text-center">
                                        <th>Sl. No.</th>
                                        <th>Income</th>
                                        <th>Source</th>
                                        <th>Currency</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {incomes.map(item => (
                                        <tr key={item.id} className="text-center">
                                            <td>{item.id}</td>
                                            <td>{item.income}</td>
                                            <td>{item.incomeSource.income_source}</td>
                                            <td>{item.currency}</td>
                                            <td>
                                                <div className="d-flex justify-content-center">
                                                    <button type="button" onClick={()=> handleView(item)}
                                                        className="btn btn-outline-secondary btn-sm me-2">
                                                        <VisibilityIcon />
                                                    </button>
                                                    <button type="button" onClick={() => handleEditModal(item)}
                                                        className="btn btn-outline-secondary btn-sm me-2">
                                                        <EditIcon />
                                                    </button>
                                                    <button type="button" onClick={() => handleDelete(item.id)}
                                                     className="btn btn-outline-danger btn-sm">
                                                        <DeleteIcon />
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>

                            <div className="d-flex justify-content-between align-items-center mt-3">
                                <button onClick={handlePreviousPage} disabled={page === 0} className="btn btn-secondary btn-sm">
                                    Previous
                                </button>
                                <span>Page {page + 1} of {totalPages}</span>
                                <button onClick={handleNextPage} disabled={page >= totalPages - 1} className="btn btn-secondary btn-sm">
                                    Next
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                {/* edit modal */}


                <div>
                    <Modal show={editModal} onHide={editModalClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal heading</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form.Group>
                                <Form.Label>Income</Form.Label>
                                <Form.Control type="number"
                                    value={editItem?.income ?? ''}
                                    onChange={(e) => setEditItem({ ...editItem, income: e.target.value })}
                                />
                                {errors.income && <span style={{ color: 'red' }}>{errors.income}</span>}
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Income Source</Form.Label>
                                <Form.Select
                                    value={editItem?.incomeSource?.id ?? ''}
                                    onChange={(e) => setEditItem({ ...editItem, income_source: e.target.value })}
                                >   
                                    {incomeSources.map(item => (
                                        <option key={item.key} value={item.key}>{item.val}</option>
                                    ))}
                                </Form.Select>
                                {/* Display validation error if any */}
                                {errors.income_source && <span style={{ color: 'red' }}>{errors.income_source}</span>}
                               
                            </Form.Group>
                            {console.log(incomeSources)}
                            <Form.Group>
                                <Form.Label>Currency</Form.Label>
                                <Form.Select
                                    value={editItem?.currency ?? ''}
                                    onChange={(e) => setEditItem({ ...editItem, currency: e.target.value })}
                                >
                                    {Array.from(currencyOptions).map(([key, val]) => (
                                            <option key={key} value={key}>{val}</option>
                                    ))}
                                </Form.Select>
                                {/* Display validation error if any */}
                                {errors.currency && <span style={{ color: 'red' }}>{errors.currency}</span>}
                            </Form.Group>
                             <Form.Group>
                                <Form.Label>Date</Form.Label>
                                <Form.Control type="date"
                                    value={editItem?.date ?? ''}
                                    onChange={(e) => setEditItem({ ...editItem, date: e.target.value })}
                                />
                                {errors.date && <span style={{ color: 'red' }}>{errors.date}</span>}
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Description</Form.Label>
                                <Form.Control as="textarea" rows={2}
                                    value={editItem?.description ?? ''}
                                    onChange={(e) => setEditItem({ ...editItem, description: e.target.value })}
                                />
                            </Form.Group>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={editModalClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={handleEditSubmission}>
                                Save Changes
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </div>

            </div>

            <div className="row gx-5 pt-2">
                    <div className="col-12">
                        <div className="card">
                            <div className="card-header">
                                View
                            </div>
                            <div className="card-body">
                                {selectedIncome ? (
                                    <div className="row">
                                        <div className="col-md-3">
                                            <h5>Income</h5>
                                            <hr />
                                            <p>{selectedIncome.income}</p>
                                        </div>
                                        <div className="col-md-3">
                                            <h5>Income Source</h5>
                                            <hr />
                                            <p>{selectedIncome.incomeSource.income_source}</p>
                                        </div>
                                        <div className="col-md-3">
                                            <h5>Description</h5>
                                            <hr />
                                            <p>{selectedIncome.description}</p>
                                        </div>
                                        <div className="col-md-3">
                                            <h5>Date</h5>
                                            <hr />
                                            <p>{selectedIncome.date}</p>
                                        </div>
                                    </div>
                                ) : (
                                    <p>No data selected</p>
                                )}
                            </div>
                        </div>
                    </div>
                </div>

        </div>
    );
}
