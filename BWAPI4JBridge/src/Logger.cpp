////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

#include "Logger.h"

#include <iostream>

namespace BWAPI4JBridge {
Logger::Logger(const std::string &name) {
  spdlog::set_pattern("[%Y-%m-%d %H:%M:%S.%f] [%n] [%P:%t] [%l] %v");

  try {
    const std::string filePath = "bwapi-data/write/" + name + ".log";
    _logger = spdlog::basic_logger_mt(name, filePath.c_str());
  } catch (const spdlog::spdlog_ex &basicLoggerException) {
    std::cout << "failed to create basic_logger_mt: " << basicLoggerException.what() << std::endl;

    try {
      _logger = spdlog::stdout_logger_mt(name);
    } catch (const spdlog::spdlog_ex &stdoutLoggerException) {
      std::cout << "failed to create stdout_logger_mt: " << stdoutLoggerException.what() << std::endl;

      throw stdoutLoggerException;
    }
  }

  _logger->set_level(spdlog::level::debug);
}
}  // namespace BWAPI4JBridge
