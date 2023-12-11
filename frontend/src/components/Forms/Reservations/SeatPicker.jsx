// import React, { useState } from "react";
import PropTypes from "prop-types";
import { Checkbox, Col, Row } from 'antd';

const onChange = (checkedValues) => {
  console.log('checked = ', checkedValues);
};

// eslint-disable-next-line no-unused-vars
const SeatPicker = ({ seatSpace, onClose }) => {

    return (
        <>
            <div className="flex flex-col text-center fixed top-32 left-10  bg-gray-100 rounded-md border border-black p-4">
                <div className="w-full mb-4 border-b border-gray-700 text-black">Screen</div>
                <div>
                    <Checkbox.Group
                        style={{
                            width: '100%',
                        }}
                        onChange={onChange}
                    >
                        <Row>
                            <Col span={8}>
                                <Checkbox value="A">A</Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox value="B">B</Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox value="C">C</Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox value="D">D</Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox value="E">E</Checkbox>
                            </Col>
                        </Row>
                    </Checkbox.Group>
                </div>
                <button type="submit" className="mt-4 text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer">
                    Reserve
                </button>
                <a className="text-black mt-4 text-sm cursor-pointer" onClick={onClose}>Cancel</a>
            </div>
        </>
    )
}

export default SeatPicker;

SeatPicker.propTypes = {
    seatSpace: PropTypes.any,
    onClose: PropTypes.func,
}